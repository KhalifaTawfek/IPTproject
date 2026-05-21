package com.nexustradex.dto;

import java.math.BigDecimal;
import java.util.List;

public class DashboardSummaryDTO {

    private Long agentId;
    private String agentName;
    private String agentStatus;
    private BigDecimal totalBankroll;
    private BigDecimal initialDeposit;
    private BigDecimal totalProfitLoss;
    private double winRate;
    private long totalTrades;
    private long closedTrades;
    private long winningTrades;
    private long losingTrades;
    private long pendingApprovalTrades;
    private List<TradeDTO> recentTrades;
    private List<TradeDTO> pendingSuggestions;

    public DashboardSummaryDTO() {}

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private Long agentId; private String agentName; private String agentStatus;
        private BigDecimal totalBankroll; private BigDecimal initialDeposit;
        private BigDecimal totalProfitLoss; private double winRate;
        private long totalTrades; private long closedTrades; private long winningTrades;
        private long losingTrades; private long pendingApprovalTrades;
        private List<TradeDTO> recentTrades; private List<TradeDTO> pendingSuggestions;

        public Builder agentId(Long v) { this.agentId = v; return this; }
        public Builder agentName(String v) { this.agentName = v; return this; }
        public Builder agentStatus(String v) { this.agentStatus = v; return this; }
        public Builder totalBankroll(BigDecimal v) { this.totalBankroll = v; return this; }
        public Builder initialDeposit(BigDecimal v) { this.initialDeposit = v; return this; }
        public Builder totalProfitLoss(BigDecimal v) { this.totalProfitLoss = v; return this; }
        public Builder winRate(double v) { this.winRate = v; return this; }
        public Builder totalTrades(long v) { this.totalTrades = v; return this; }
        public Builder closedTrades(long v) { this.closedTrades = v; return this; }
        public Builder winningTrades(long v) { this.winningTrades = v; return this; }
        public Builder losingTrades(long v) { this.losingTrades = v; return this; }
        public Builder pendingApprovalTrades(long v) { this.pendingApprovalTrades = v; return this; }
        public Builder recentTrades(List<TradeDTO> v) { this.recentTrades = v; return this; }
        public Builder pendingSuggestions(List<TradeDTO> v) { this.pendingSuggestions = v; return this; }

        public DashboardSummaryDTO build() {
            DashboardSummaryDTO d = new DashboardSummaryDTO();
            d.agentId = agentId; d.agentName = agentName; d.agentStatus = agentStatus;
            d.totalBankroll = totalBankroll; d.initialDeposit = initialDeposit;
            d.totalProfitLoss = totalProfitLoss; d.winRate = winRate;
            d.totalTrades = totalTrades; d.closedTrades = closedTrades;
            d.winningTrades = winningTrades; d.losingTrades = losingTrades;
            d.pendingApprovalTrades = pendingApprovalTrades;
            d.recentTrades = recentTrades; d.pendingSuggestions = pendingSuggestions;
            return d;
        }
    }

    public Long getAgentId() { return agentId; }
    public String getAgentName() { return agentName; }
    public String getAgentStatus() { return agentStatus; }
    public BigDecimal getTotalBankroll() { return totalBankroll; }
    public BigDecimal getInitialDeposit() { return initialDeposit; }
    public BigDecimal getTotalProfitLoss() { return totalProfitLoss; }
    public double getWinRate() { return winRate; }
    public long getTotalTrades() { return totalTrades; }
    public long getClosedTrades() { return closedTrades; }
    public long getWinningTrades() { return winningTrades; }
    public long getLosingTrades() { return losingTrades; }
    public long getPendingApprovalTrades() { return pendingApprovalTrades; }
    public List<TradeDTO> getRecentTrades() { return recentTrades; }
    public List<TradeDTO> getPendingSuggestions() { return pendingSuggestions; }
}
