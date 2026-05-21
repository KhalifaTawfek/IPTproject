package com.nexustradex.service;

import com.nexustradex.dto.DashboardSummaryDTO;
import com.nexustradex.dto.TradeDTO;
import com.nexustradex.model.Trade;
import com.nexustradex.model.TradingAgent;
import com.nexustradex.repository.TradeRepository;
import com.nexustradex.repository.TradingAgentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TradeService {

    private final TradeRepository tradeRepository;
    private final TradingAgentRepository agentRepository;

    public TradeService(TradeRepository tradeRepository, TradingAgentRepository agentRepository) {
        this.tradeRepository = tradeRepository;
        this.agentRepository = agentRepository;
    }

    public DashboardSummaryDTO getDashboardSummary(Long agentId) {
        TradingAgent agent = agentRepository.findById(agentId)
                .orElseThrow(() -> new RuntimeException("Agent not found: " + agentId));

        long closedTrades = tradeRepository.countClosedTradesByAgentId(agentId);
        long winningTrades = tradeRepository.countWinsByAgentId(agentId);
        BigDecimal totalPnL = tradeRepository.sumProfitLossByAgentId(agentId);

        double winRate = closedTrades > 0 ? (double) winningTrades / closedTrades * 100 : 0.0;

        List<Trade> allTrades = tradeRepository.findByAgentIdOrderByCreatedAtDesc(agentId);
        List<Trade> pending = tradeRepository.findByAgentIdAndStatus(agentId, Trade.TradeStatus.PENDING_APPROVAL);

        List<TradeDTO> recentTrades = allTrades.stream().limit(10).map(this::toDTO).collect(Collectors.toList());
        List<TradeDTO> pendingSuggestions = pending.stream().map(this::toDTO).collect(Collectors.toList());

        return DashboardSummaryDTO.builder()
                .agentId(agent.getId())
                .agentName(agent.getName())
                .agentStatus(agent.getStatus().name())
                .totalBankroll(agent.getBankroll())
                .initialDeposit(agent.getInitialDeposit())
                .totalProfitLoss(totalPnL)
                .winRate(Math.round(winRate * 10.0) / 10.0)
                .totalTrades(allTrades.size())
                .closedTrades(closedTrades)
                .winningTrades(winningTrades)
                .losingTrades(closedTrades - winningTrades)
                .pendingApprovalTrades(pending.size())
                .recentTrades(recentTrades)
                .pendingSuggestions(pendingSuggestions)
                .build();
    }

    @Transactional
    public TradeDTO approveTrade(Long tradeId) {
        Trade trade = tradeRepository.findById(tradeId)
                .orElseThrow(() -> new RuntimeException("Trade not found: " + tradeId));
        if (trade.getStatus() != Trade.TradeStatus.PENDING_APPROVAL) {
            throw new IllegalStateException("Trade is not pending approval");
        }
        trade.setStatus(Trade.TradeStatus.APPROVED);
        trade.setExecutedAt(LocalDateTime.now());
        return toDTO(tradeRepository.save(trade));
    }

    @Transactional
    public TradeDTO rejectTrade(Long tradeId) {
        Trade trade = tradeRepository.findById(tradeId)
                .orElseThrow(() -> new RuntimeException("Trade not found: " + tradeId));
        if (trade.getStatus() != Trade.TradeStatus.PENDING_APPROVAL) {
            throw new IllegalStateException("Trade is not pending approval");
        }
        trade.setStatus(Trade.TradeStatus.REJECTED);
        return toDTO(tradeRepository.save(trade));
    }

    public List<TradeDTO> getTradesForAgent(Long agentId) {
        return tradeRepository.findByAgentIdOrderByCreatedAtDesc(agentId)
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    private TradeDTO toDTO(Trade t) {
        return TradeDTO.builder()
                .id(t.getId())
                .market(t.getMarket())
                .type(t.getType().name())
                .marketType(t.getMarketType().name())
                .amount(t.getAmount())
                .entryPrice(t.getEntryPrice())
                .exitPrice(t.getExitPrice())
                .profitLoss(t.getProfitLoss())
                .status(t.getStatus().name())
                .createdAt(t.getCreatedAt())
                .executedAt(t.getExecutedAt())
                .aiReason(t.getAiReason())
                .aiConfidence(t.getAiConfidence())
                .build();
    }
}
