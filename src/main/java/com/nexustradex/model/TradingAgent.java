package com.nexustradex.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "trading_agents")
public class TradingAgent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String apiKey;

    @Column(nullable = false, precision = 18, scale = 2)
    private BigDecimal bankroll;

    @Column(nullable = false, precision = 18, scale = 2)
    private BigDecimal initialDeposit;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AgentStatus status;

    @Column(nullable = false)
    private boolean requiresApproval;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "agent", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Trade> trades;

    public TradingAgent() {}

    @PrePersist
    public void prePersist() {
        if (this.createdAt == null) this.createdAt = LocalDateTime.now();
    }

    public enum AgentStatus { ACTIVE, PAUSED, DISCONNECTED }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private Long id;
        private String name;
        private String apiKey;
        private BigDecimal bankroll;
        private BigDecimal initialDeposit;
        private AgentStatus status;
        private boolean requiresApproval;

        public Builder id(Long id) { this.id = id; return this; }
        public Builder name(String name) { this.name = name; return this; }
        public Builder apiKey(String apiKey) { this.apiKey = apiKey; return this; }
        public Builder bankroll(BigDecimal bankroll) { this.bankroll = bankroll; return this; }
        public Builder initialDeposit(BigDecimal initialDeposit) { this.initialDeposit = initialDeposit; return this; }
        public Builder status(AgentStatus status) { this.status = status; return this; }
        public Builder requiresApproval(boolean requiresApproval) { this.requiresApproval = requiresApproval; return this; }

        public TradingAgent build() {
            TradingAgent a = new TradingAgent();
            a.id = id; a.name = name; a.apiKey = apiKey; a.bankroll = bankroll;
            a.initialDeposit = initialDeposit; a.status = status; a.requiresApproval = requiresApproval;
            return a;
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getApiKey() { return apiKey; }
    public void setApiKey(String apiKey) { this.apiKey = apiKey; }
    public BigDecimal getBankroll() { return bankroll; }
    public void setBankroll(BigDecimal bankroll) { this.bankroll = bankroll; }
    public BigDecimal getInitialDeposit() { return initialDeposit; }
    public void setInitialDeposit(BigDecimal initialDeposit) { this.initialDeposit = initialDeposit; }
    public AgentStatus getStatus() { return status; }
    public void setStatus(AgentStatus status) { this.status = status; }
    public boolean isRequiresApproval() { return requiresApproval; }
    public void setRequiresApproval(boolean requiresApproval) { this.requiresApproval = requiresApproval; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public List<Trade> getTrades() { return trades; }
    public void setTrades(List<Trade> trades) { this.trades = trades; }
}
