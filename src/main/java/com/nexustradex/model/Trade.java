package com.nexustradex.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "trades")
public class Trade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "agent_id", nullable = false)
    private TradingAgent agent;

    @Column(nullable = false)
    private String market;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TradeType type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MarketType marketType;

    @Column(nullable = false, precision = 18, scale = 8)
    private BigDecimal amount;

    @Column(nullable = false, precision = 18, scale = 8)
    private BigDecimal entryPrice;

    @Column(precision = 18, scale = 8)
    private BigDecimal exitPrice;

    @Column(precision = 18, scale = 8)
    private BigDecimal profitLoss;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TradeStatus status;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    private LocalDateTime executedAt;
    private LocalDateTime closedAt;

    @Column(length = 1000)
    private String aiReason;

    @Column
    private Integer aiConfidence;

    public Trade() {}

    @PrePersist
    public void prePersist() {
        if (this.createdAt == null) this.createdAt = LocalDateTime.now();
    }

    public enum TradeType { BUY, SELL }
    public enum MarketType { STOCK, CRYPTO, POLYMARKET }
    public enum TradeStatus { PENDING_APPROVAL, APPROVED, REJECTED, EXECUTED, CLOSED }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private Long id;
        private TradingAgent agent;
        private String market;
        private TradeType type;
        private MarketType marketType;
        private BigDecimal amount;
        private BigDecimal entryPrice;
        private BigDecimal exitPrice;
        private BigDecimal profitLoss;
        private TradeStatus status;
        private LocalDateTime createdAt;
        private LocalDateTime executedAt;
        private LocalDateTime closedAt;
        private String aiReason;
        private Integer aiConfidence;

        public Builder id(Long id) { this.id = id; return this; }
        public Builder agent(TradingAgent agent) { this.agent = agent; return this; }
        public Builder market(String market) { this.market = market; return this; }
        public Builder type(TradeType type) { this.type = type; return this; }
        public Builder marketType(MarketType marketType) { this.marketType = marketType; return this; }
        public Builder amount(BigDecimal amount) { this.amount = amount; return this; }
        public Builder entryPrice(BigDecimal entryPrice) { this.entryPrice = entryPrice; return this; }
        public Builder exitPrice(BigDecimal exitPrice) { this.exitPrice = exitPrice; return this; }
        public Builder profitLoss(BigDecimal profitLoss) { this.profitLoss = profitLoss; return this; }
        public Builder status(TradeStatus status) { this.status = status; return this; }
        public Builder createdAt(LocalDateTime v) { this.createdAt = v; return this; }
        public Builder executedAt(LocalDateTime v) { this.executedAt = v; return this; }
        public Builder closedAt(LocalDateTime v) { this.closedAt = v; return this; }
        public Builder aiReason(String aiReason) { this.aiReason = aiReason; return this; }
        public Builder aiConfidence(Integer aiConfidence) { this.aiConfidence = aiConfidence; return this; }

        public Trade build() {
            Trade t = new Trade();
            t.id = id; t.agent = agent; t.market = market; t.type = type;
            t.marketType = marketType; t.amount = amount; t.entryPrice = entryPrice;
            t.exitPrice = exitPrice; t.profitLoss = profitLoss; t.status = status;
            t.createdAt = createdAt; t.executedAt = executedAt; t.closedAt = closedAt;
            t.aiReason = aiReason; t.aiConfidence = aiConfidence;
            return t;
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public TradingAgent getAgent() { return agent; }
    public void setAgent(TradingAgent agent) { this.agent = agent; }
    public String getMarket() { return market; }
    public void setMarket(String market) { this.market = market; }
    public TradeType getType() { return type; }
    public void setType(TradeType type) { this.type = type; }
    public MarketType getMarketType() { return marketType; }
    public void setMarketType(MarketType marketType) { this.marketType = marketType; }
    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    public BigDecimal getEntryPrice() { return entryPrice; }
    public void setEntryPrice(BigDecimal entryPrice) { this.entryPrice = entryPrice; }
    public BigDecimal getExitPrice() { return exitPrice; }
    public void setExitPrice(BigDecimal exitPrice) { this.exitPrice = exitPrice; }
    public BigDecimal getProfitLoss() { return profitLoss; }
    public void setProfitLoss(BigDecimal profitLoss) { this.profitLoss = profitLoss; }
    public TradeStatus getStatus() { return status; }
    public void setStatus(TradeStatus status) { this.status = status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime v) { this.createdAt = v; }
    public LocalDateTime getExecutedAt() { return executedAt; }
    public void setExecutedAt(LocalDateTime v) { this.executedAt = v; }
    public LocalDateTime getClosedAt() { return closedAt; }
    public void setClosedAt(LocalDateTime v) { this.closedAt = v; }
    public String getAiReason() { return aiReason; }
    public void setAiReason(String aiReason) { this.aiReason = aiReason; }
    public Integer getAiConfidence() { return aiConfidence; }
    public void setAiConfidence(Integer aiConfidence) { this.aiConfidence = aiConfidence; }
}
