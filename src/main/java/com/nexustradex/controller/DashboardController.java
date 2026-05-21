package com.nexustradex.controller;

import com.nexustradex.dto.DashboardSummaryDTO;
import com.nexustradex.dto.TradeDTO;
import com.nexustradex.service.TradeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class DashboardController {

    private final TradeService tradeService;

    public DashboardController(TradeService tradeService) {
        this.tradeService = tradeService;
    }

    @GetMapping("/dashboard/{agentId}")
    public ResponseEntity<DashboardSummaryDTO> getDashboard(@PathVariable Long agentId) {
        return ResponseEntity.ok(tradeService.getDashboardSummary(agentId));
    }

    @GetMapping("/agents/{agentId}/trades")
    public ResponseEntity<List<TradeDTO>> getTrades(@PathVariable Long agentId) {
        return ResponseEntity.ok(tradeService.getTradesForAgent(agentId));
    }

    @PostMapping("/trades/{tradeId}/approve")
    public ResponseEntity<TradeDTO> approveTrade(@PathVariable Long tradeId) {
        return ResponseEntity.ok(tradeService.approveTrade(tradeId));
    }

    @PostMapping("/trades/{tradeId}/reject")
    public ResponseEntity<TradeDTO> rejectTrade(@PathVariable Long tradeId) {
        return ResponseEntity.ok(tradeService.rejectTrade(tradeId));
    }
}
