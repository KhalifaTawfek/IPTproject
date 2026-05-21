package com.nexustradex.config;

import com.nexustradex.model.Trade;
import com.nexustradex.model.TradingAgent;
import com.nexustradex.model.User;
import com.nexustradex.repository.TradeRepository;
import com.nexustradex.repository.TradingAgentRepository;
import com.nexustradex.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Configuration
public class DataSeeder {

    @Bean
    @Transactional
    public CommandLineRunner seedData(TradingAgentRepository agentRepo,
                                      TradeRepository tradeRepo,
                                      UserRepository userRepo,
                                      PasswordEncoder passwordEncoder) {
        return args -> {

            // ===== ADMIN =====
            TradingAgent adminAgent = agentRepo.save(TradingAgent.builder()
                    .name("Admin Monitor")
                    .apiKey("key-admin-000")
                    .bankroll(new BigDecimal("10000.00"))
                    .initialDeposit(new BigDecimal("10000.00"))
                    .status(TradingAgent.AgentStatus.ACTIVE)
                    .requiresApproval(false)
                    .build());

            User admin = new User();
            admin.setUsername("admin");
            admin.setEmail("admin@nexustradex.com");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRole(User.Role.ADMIN);
            admin.setEnabled(true);
            admin.setAgent(adminAgent);
            userRepo.save(admin);

            // ===== ALEX — momentum trader =====
            TradingAgent alexAgent = agentRepo.save(TradingAgent.builder()
                    .name("AlphaBot-7 (Alex)")
                    .apiKey("sk-alex-bt7-prod-2024")
                    .bankroll(new BigDecimal("1487.30"))
                    .initialDeposit(new BigDecimal("1000.00"))
                    .status(TradingAgent.AgentStatus.ACTIVE)
                    .requiresApproval(false)
                    .build());

            User alex = new User();
            alex.setUsername("alex");
            alex.setEmail("alex@example.com");
            alex.setPassword(passwordEncoder.encode("password123"));
            alex.setRole(User.Role.USER);
            alex.setEnabled(true);
            alex.setAgent(alexAgent);
            userRepo.save(alex);

            // Closed wins
            tradeRepo.save(trade(alexAgent, "BTC/USD", Trade.TradeType.BUY, Trade.MarketType.CRYPTO,
                    "500", "61200.00", "67800.00", "53.93", Trade.TradeStatus.CLOSED,
                    "RSI oversold on 4H + MACD bullish crossover. Strong support at 60K.", 88,
                    minus(45), minus(44), minus(36)));

            tradeRepo.save(trade(alexAgent, "NVDA", Trade.TradeType.BUY, Trade.MarketType.STOCK,
                    "2", "850.00", "942.00", "184.00", Trade.TradeStatus.CLOSED,
                    "Earnings beat expectations by 18%. GPU demand from AI sector remains strong.", 91,
                    minus(40), minus(39), minus(30)));

            tradeRepo.save(trade(alexAgent, "ETH/USD", Trade.TradeType.BUY, Trade.MarketType.CRYPTO,
                    "300", "3100.00", "3650.00", "53.23", Trade.TradeStatus.CLOSED,
                    "EIP-4844 upgrade catalyst. Staking yield uptick signals institutional inflow.", 85,
                    minus(35), minus(34), minus(25)));

            tradeRepo.save(trade(alexAgent, "AAPL", Trade.TradeType.BUY, Trade.MarketType.STOCK,
                    "150", "178.50", "192.40", "20.85", Trade.TradeStatus.CLOSED,
                    "iPhone 16 pre-orders above analyst estimates. Services segment accelerating.", 79,
                    minus(28), minus(27), minus(18)));

            tradeRepo.save(trade(alexAgent, "SOL/USD", Trade.TradeType.BUY, Trade.MarketType.CRYPTO,
                    "200", "145.00", "189.00", "60.69", Trade.TradeStatus.CLOSED,
                    "Network TVL hitting ATH. DeFi volume surge on Solana outpacing Ethereum.", 82,
                    minus(22), minus(21), minus(12)));

            // Closed loss
            tradeRepo.save(trade(alexAgent, "TSLA", Trade.TradeType.SELL, Trade.MarketType.STOCK,
                    "100", "248.00", "271.00", "-23.00", Trade.TradeStatus.CLOSED,
                    "Overbought on RSI, delivery miss expected. Short opportunity.", 62,
                    minus(18), minus(17), minus(10)));

            // Active / pending
            tradeRepo.save(trade(alexAgent, "MSFT", Trade.TradeType.BUY, Trade.MarketType.STOCK,
                    "200", "415.00", null, null, Trade.TradeStatus.EXECUTED,
                    "Azure cloud revenue growing 28% YoY. Copilot adoption accelerating.", 87,
                    minus(5), minus(4), null));

            tradeRepo.save(trade(alexAgent, "BNB/USD", Trade.TradeType.BUY, Trade.MarketType.CRYPTO,
                    "150", "412.00", null, null, Trade.TradeStatus.PENDING_APPROVAL,
                    "BSC ecosystem growth + BNB burn rate at 6-month high. Breakout from 3-week consolidation.", 74,
                    minus(1), null, null));

            // Polymarket trades
            tradeRepo.save(trade(alexAgent, "Will Fed cut rates in Dec?", Trade.TradeType.BUY, Trade.MarketType.POLYMARKET,
                    "100", "0.62", "1.00", "60.48", Trade.TradeStatus.CLOSED,
                    "CME FedWatch tool showing 78% probability. Inflation trending toward 2% target.", 80,
                    minus(20), minus(19), minus(8)));

            tradeRepo.save(trade(alexAgent, "Bitcoin > $75K by year end?", Trade.TradeType.BUY, Trade.MarketType.POLYMARKET,
                    "80", "0.55", null, null, Trade.TradeStatus.EXECUTED,
                    "Historical halving cycle pattern + ETF inflows of $500M+ weekly.", 72,
                    minus(6), minus(5), null));

            // ===== SARA — cautious, manual approval =====
            TradingAgent saraAgent = agentRepo.save(TradingAgent.builder()
                    .name("CautiousAI (Sara)")
                    .apiKey("sk-sara-cautious-v2")
                    .bankroll(new BigDecimal("1134.75"))
                    .initialDeposit(new BigDecimal("800.00"))
                    .status(TradingAgent.AgentStatus.ACTIVE)
                    .requiresApproval(true)
                    .build());

            User sara = new User();
            sara.setUsername("sara");
            sara.setEmail("sara@example.com");
            sara.setPassword(passwordEncoder.encode("password123"));
            sara.setRole(User.Role.USER);
            sara.setEnabled(true);
            sara.setAgent(saraAgent);
            userRepo.save(sara);

            tradeRepo.save(trade(saraAgent, "BTC/USD", Trade.TradeType.BUY, Trade.MarketType.CRYPTO,
                    "200", "62000.00", "65800.00", "12.26", Trade.TradeStatus.CLOSED,
                    "Conservative spot buy. Weekly MA200 acting as support. Low risk, small position.", 76,
                    minus(30), minus(29), minus(18)));

            tradeRepo.save(trade(saraAgent, "GOOGL", Trade.TradeType.BUY, Trade.MarketType.STOCK,
                    "180", "168.50", "181.20", "22.86", Trade.TradeStatus.CLOSED,
                    "P/E below 5-year average. Search ad revenue stable. YouTube Premium growing.", 81,
                    minus(25), minus(24), minus(14)));

            tradeRepo.save(trade(saraAgent, "ETH/USD", Trade.TradeType.BUY, Trade.MarketType.CRYPTO,
                    "150", "3200.00", "3410.00", "9.84", Trade.TradeStatus.CLOSED,
                    "Staking yield at 4.2% APR. Low-risk accumulation at current levels.", 78,
                    minus(20), minus(19), minus(10)));

            tradeRepo.save(trade(saraAgent, "SPY", Trade.TradeType.BUY, Trade.MarketType.STOCK,
                    "250", "512.00", "528.50", "8.06", Trade.TradeStatus.CLOSED,
                    "Broad market ETF hedge. S&P 500 earnings season historically bullish.", 83,
                    minus(15), minus(14), minus(6)));

            tradeRepo.save(trade(saraAgent, "AMZN", Trade.TradeType.BUY, Trade.MarketType.STOCK,
                    "120", "196.00", null, null, Trade.TradeStatus.EXECUTED,
                    "AWS margin expansion + Prime subscriber growth. Logistics efficiency improving.", 77,
                    minus(4), minus(3), null));

            // Pending approvals for Sara
            tradeRepo.save(trade(saraAgent, "LTC/USD", Trade.TradeType.BUY, Trade.MarketType.CRYPTO,
                    "100", "88.50", null, null, Trade.TradeStatus.PENDING_APPROVAL,
                    "Litecoin halving in 6 months. Historical 150% pre-halving run pattern observed.", 68,
                    minus(1), null, null));

            tradeRepo.save(trade(saraAgent, "Will AI replace most jobs by 2030?", Trade.TradeType.SELL, Trade.MarketType.POLYMARKET,
                    "60", "0.38", null, null, Trade.TradeStatus.PENDING_APPROVAL,
                    "Overpriced given regulatory lag and labor market dynamics. Short at current price.", 65,
                    minus(0), null, null));

            // ===== MARCO — high risk, paused =====
            TradingAgent marcoAgent = agentRepo.save(TradingAgent.builder()
                    .name("Degen-99 (Marco)")
                    .apiKey("sk-marco-degen99")
                    .bankroll(new BigDecimal("187.40"))
                    .initialDeposit(new BigDecimal("500.00"))
                    .status(TradingAgent.AgentStatus.PAUSED)
                    .requiresApproval(false)
                    .build());

            User marco = new User();
            marco.setUsername("marco");
            marco.setEmail("marco@example.com");
            marco.setPassword(passwordEncoder.encode("password123"));
            marco.setRole(User.Role.USER);
            marco.setEnabled(true);
            marco.setAgent(marcoAgent);
            userRepo.save(marco);

            tradeRepo.save(trade(marcoAgent, "DOGE/USD", Trade.TradeType.BUY, Trade.MarketType.CRYPTO,
                    "300", "0.18", "0.12", "-33.33", Trade.TradeStatus.CLOSED,
                    "High risk meme trade. Elon Twitter activity spike. Pure momentum play.", 44,
                    minus(38), minus(37), minus(25)));

            tradeRepo.save(trade(marcoAgent, "PEPE/USD", Trade.TradeType.BUY, Trade.MarketType.CRYPTO,
                    "200", "0.0000134", "0.0000089", "-67.16", Trade.TradeStatus.CLOSED,
                    "Meme season + trending on X. Entered late. Stop-loss triggered.", 35,
                    minus(30), minus(29), minus(20)));

            tradeRepo.save(trade(marcoAgent, "GME", Trade.TradeType.BUY, Trade.MarketType.STOCK,
                    "150", "22.40", "18.10", "-28.79", Trade.TradeStatus.CLOSED,
                    "Roaring Kitty activity detected. Short squeeze potential. Missed the window.", 38,
                    minus(22), minus(21), minus(14)));

            tradeRepo.save(trade(marcoAgent, "SHIB/USD", Trade.TradeType.BUY, Trade.MarketType.CRYPTO,
                    "100", "0.0000295", "0.0000312", "5.76", Trade.TradeStatus.CLOSED,
                    "SHIB burn rate 300% increase. Shibarium L2 adoption growing.", 52,
                    minus(15), minus(14), minus(8)));

            tradeRepo.save(trade(marcoAgent, "WIF/USD", Trade.TradeType.BUY, Trade.MarketType.CRYPTO,
                    "80", "3.12", "2.45", "-17.18", Trade.TradeStatus.CLOSED,
                    "Top dog themed coin. Failed breakout. Agent now paused for review.", 41,
                    minus(10), minus(9), minus(5)));

            // ===== ELENA — forex-focused, new user =====
            TradingAgent elenaAgent = agentRepo.save(TradingAgent.builder()
                    .name("ForexFlow (Elena)")
                    .apiKey("sk-elena-forex-v1")
                    .bankroll(new BigDecimal("2310.00"))
                    .initialDeposit(new BigDecimal("2000.00"))
                    .status(TradingAgent.AgentStatus.ACTIVE)
                    .requiresApproval(true)
                    .build());

            User elena = new User();
            elena.setUsername("elena");
            elena.setEmail("elena@example.com");
            elena.setPassword(passwordEncoder.encode("password123"));
            elena.setRole(User.Role.USER);
            elena.setEnabled(true);
            elena.setAgent(elenaAgent);
            userRepo.save(elena);

            tradeRepo.save(trade(elenaAgent, "AAPL", Trade.TradeType.BUY, Trade.MarketType.STOCK,
                    "300", "182.00", "197.50", "25.55", Trade.TradeStatus.CLOSED,
                    "Post-WWDC seasonal rally. Vision Pro reviews positive. Services margin 74%.", 86,
                    minus(25), minus(24), minus(12)));

            tradeRepo.save(trade(elenaAgent, "META", Trade.TradeType.BUY, Trade.MarketType.STOCK,
                    "200", "495.00", "531.00", "14.55", Trade.TradeStatus.CLOSED,
                    "Reality Labs losses declining. Ad revenue per user hitting record. Threads 200M users.", 84,
                    minus(18), minus(17), minus(7)));

            tradeRepo.save(trade(elenaAgent, "XRP/USD", Trade.TradeType.BUY, Trade.MarketType.CRYPTO,
                    "250", "0.52", "0.71", "91.35", Trade.TradeStatus.CLOSED,
                    "SEC lawsuit partial victory. Ripple institutional partnerships expanding globally.", 77,
                    minus(12), minus(11), minus(4)));

            tradeRepo.save(trade(elenaAgent, "NVDA", Trade.TradeType.BUY, Trade.MarketType.STOCK,
                    "180", "875.00", null, null, Trade.TradeStatus.EXECUTED,
                    "Blackwell GPU cycle ahead. Data center demand exceeds supply through 2025.", 92,
                    minus(3), minus(2), null));

            tradeRepo.save(trade(elenaAgent, "Will Trump win 2024 election?", Trade.TradeType.BUY, Trade.MarketType.POLYMARKET,
                    "120", "0.48", "1.00", "150.00", Trade.TradeStatus.CLOSED,
                    "Polling data, early vote patterns, and swing state momentum analysis.", 71,
                    minus(16), minus(15), minus(5)));

            tradeRepo.save(trade(elenaAgent, "ADA/USD", Trade.TradeType.BUY, Trade.MarketType.CRYPTO,
                    "100", "0.48", null, null, Trade.TradeStatus.PENDING_APPROVAL,
                    "Chang hard fork scheduled. DeFi TVL recovering on Cardano. Risk: low liquidity.", 63,
                    minus(0), null, null));

            System.out.println("✅ Data seeded: 4 users, 4 agents, 30+ trades across STOCK/CRYPTO/POLYMARKET");
        };
    }

    private Trade trade(TradingAgent agent, String market, Trade.TradeType type,
                        Trade.MarketType mkt, String amount, String entry, String exit,
                        String pnl, Trade.TradeStatus status, String aiReason, int confidence,
                        LocalDateTime created, LocalDateTime executed, LocalDateTime closed) {
        return Trade.builder()
                .agent(agent)
                .market(market)
                .type(type)
                .marketType(mkt)
                .amount(new BigDecimal(amount))
                .entryPrice(new BigDecimal(entry))
                .exitPrice(exit != null ? new BigDecimal(exit) : null)
                .profitLoss(pnl != null ? new BigDecimal(pnl) : null)
                .status(status)
                .aiReason(aiReason)
                .aiConfidence(confidence)
                .createdAt(created)
                .executedAt(executed)
                .closedAt(closed)
                .build();
    }

    private LocalDateTime minus(int days) {
        return LocalDateTime.now().minusDays(days);
    }
}
