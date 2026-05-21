package com.nexustradex.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TradeDTO {

    private Long id;
    private String market;
    private String type;
    private String marketType;
    private BigDecimal amount;
    private BigDecimal entryPrice;
    private BigDecimal exitPrice;
    private BigDecimal profitLoss;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime executedAt;
    private String aiReason;
    private Integer aiConfidence;

    public TradeDTO() {}

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private Long id; private String market; private String type; private String marketType;
        private BigDecimal amount; private BigDecimal entryPrice; private BigDecimal exitPrice;
        private BigDecimal profitLoss; private String status;
        private LocalDateTime createdAt; private LocalDateTime executedAt;
        private String aiReason; private Integer aiConfidence;

        public Builder id(Long v) { this.id = v; return this; }
        public Builder market(String v) { this.market = v; return this; }
        public Builder type(String v) { this.type = v; return this; }
        public Builder marketType(String v) { this.marketType = v; return this; }
        public Builder amount(BigDecimal v) { this.amount = v; return this; }
        public Builder entryPrice(BigDecimal v) { this.entryPrice = v; return this; }
        public Builder exitPrice(BigDecimal v) { this.exitPrice = v; return this; }
        public Builder profitLoss(BigDecimal v) { this.profitLoss = v; return this; }
        public Builder status(String v) { this.status = v; return this; }
        public Builder createdAt(LocalDateTime v) { this.createdAt = v; return this; }
        public Builder executedAt(LocalDateTime v) { this.executedAt = v; return this; }
        public Builder aiReason(String v) { this.aiReason = v; return this; }
        public Builder aiConfidence(Integer v) { this.aiConfidence = v; return this; }

        public TradeDTO build() {
            TradeDTO d = new TradeDTO();
            d.id = id; d.market = market; d.type = type; d.marketType = marketType;
            d.amount = amount; d.entryPrice = entryPrice; d.exitPrice = exitPrice;
            d.profitLoss = profitLoss; d.status = status; d.createdAt = createdAt;
            d.executedAt = executedAt; d.aiReason = aiReason; d.aiConfidence = aiConfidence;
            return d;
        }
    }

    public Long getId() { return id; }
    public String getMarket() { return market; }
    public String getType() { return type; }
    public String getMarketType() { return marketType; }
    public BigDecimal getAmount() { return amount; }
    public BigDecimal getEntryPrice() { return entryPrice; }
    public BigDecimal getExitPrice() { return exitPrice; }
    public BigDecimal getProfitLoss() { return profitLoss; }
    public String getStatus() { return status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getExecutedAt() { return executedAt; }
    public String getAiReason() { return aiReason; }
    public Integer getAiConfidence() { return aiConfidence; }
}
