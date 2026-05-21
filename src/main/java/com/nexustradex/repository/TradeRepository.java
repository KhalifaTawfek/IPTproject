package com.nexustradex.repository;

import com.nexustradex.model.Trade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TradeRepository extends JpaRepository<Trade, Long> {

    List<Trade> findByAgentIdOrderByCreatedAtDesc(Long agentId);

    List<Trade> findByAgentIdAndStatus(Long agentId, Trade.TradeStatus status);

    @Query("SELECT COUNT(t) FROM Trade t WHERE t.agent.id = :agentId AND t.status = 'CLOSED' AND t.profitLoss > 0")
    long countWinsByAgentId(Long agentId);

    @Query("SELECT COUNT(t) FROM Trade t WHERE t.agent.id = :agentId AND t.status = 'CLOSED'")
    long countClosedTradesByAgentId(Long agentId);

    @Query("SELECT COALESCE(SUM(t.profitLoss), 0) FROM Trade t WHERE t.agent.id = :agentId AND t.status = 'CLOSED'")
    java.math.BigDecimal sumProfitLossByAgentId(Long agentId);

    List<Trade> findByStatus(Trade.TradeStatus status);
}
