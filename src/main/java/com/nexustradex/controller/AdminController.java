package com.nexustradex.controller;

import com.nexustradex.model.Trade;
import com.nexustradex.model.TradingAgent;
import com.nexustradex.model.User;
import com.nexustradex.repository.TradingAgentRepository;
import com.nexustradex.repository.TradeRepository;
import com.nexustradex.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final TradingAgentRepository agentRepository;
    private final TradeRepository tradeRepository;

    public AdminController(UserService userService,
                           TradingAgentRepository agentRepository,
                           TradeRepository tradeRepository) {
        this.userService = userService;
        this.agentRepository = agentRepository;
        this.tradeRepository = tradeRepository;
    }

    @GetMapping
    public String adminHome(Model model) {
        List<User> users = userService.findAll();
        List<TradingAgent> agents = agentRepository.findAll();
        List<Trade> pendingTrades = tradeRepository.findByStatus(Trade.TradeStatus.PENDING_APPROVAL);

        long activeAgents = agents.stream()
                .filter(a -> a.getStatus() == TradingAgent.AgentStatus.ACTIVE).count();

        model.addAttribute("users", users);
        model.addAttribute("agents", agents);
        model.addAttribute("pendingTrades", pendingTrades);
        model.addAttribute("totalUsers", users.size());
        model.addAttribute("activeAgents", activeAgents);
        model.addAttribute("pendingCount", pendingTrades.size());
        return "admin/index";
    }

    @PostMapping("/users/{userId}/toggle")
    public String toggleUser(@PathVariable Long userId, RedirectAttributes ra) {
        userService.toggleUserEnabled(userId);
        ra.addFlashAttribute("message", "User status updated.");
        return "redirect:/admin";
    }

    @PostMapping("/agents/{agentId}/toggle")
    public String toggleAgent(@PathVariable Long agentId, RedirectAttributes ra) {
        userService.toggleAgentStatus(agentId);
        ra.addFlashAttribute("message", "Agent status updated.");
        return "redirect:/admin";
    }
}
