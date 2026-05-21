package com.nexustradex.repository;

import com.nexustradex.model.TradingAgent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TradingAgentRepository extends JpaRepository<TradingAgent, Long> {

    Optional<TradingAgent> findByApiKey(String apiKey);

    Optional<TradingAgent> findByName(String name);
}
