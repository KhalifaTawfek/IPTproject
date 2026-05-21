package com.nexustradex.service;

import com.nexustradex.model.TradingAgent;
import com.nexustradex.model.User;
import com.nexustradex.repository.TradingAgentRepository;
import com.nexustradex.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final TradingAgentRepository agentRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                       TradingAgentRepository agentRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.agentRepository = agentRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public User register(String username, String email, String rawPassword) {
        if (userRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("Username already taken");
        }
        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email already registered");
        }

        // Create a trading agent for this user
        TradingAgent agent = TradingAgent.builder()
                .name(username + "'s Agent")
                .apiKey("key-" + username.toLowerCase() + "-" + System.currentTimeMillis())
                .bankroll(new BigDecimal("1000.00"))
                .initialDeposit(new BigDecimal("1000.00"))
                .status(TradingAgent.AgentStatus.DISCONNECTED)
                .requiresApproval(true)
                .build();
        agent = agentRepository.save(agent);

        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(rawPassword));
        user.setRole(User.Role.USER);
        user.setEnabled(true);
        user.setAgent(agent);

        return userRepository.save(user);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Transactional
    public void toggleUserEnabled(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setEnabled(!user.isEnabled());
        userRepository.save(user);
    }

    @Transactional
    public void updateAgentApiKey(String username, String apiKey, boolean requiresApproval) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        TradingAgent agent = user.getAgent();
        agent.setApiKey(apiKey);
        agent.setRequiresApproval(requiresApproval);
        agent.setStatus(TradingAgent.AgentStatus.ACTIVE);
        agentRepository.save(agent);
    }

    @Transactional
    public void toggleAgentStatus(Long agentId) {
        TradingAgent agent = agentRepository.findById(agentId)
                .orElseThrow(() -> new RuntimeException("Agent not found"));
        if (agent.getStatus() == TradingAgent.AgentStatus.ACTIVE) {
            agent.setStatus(TradingAgent.AgentStatus.PAUSED);
        } else {
            agent.setStatus(TradingAgent.AgentStatus.ACTIVE);
        }
        agentRepository.save(agent);
    }
}
