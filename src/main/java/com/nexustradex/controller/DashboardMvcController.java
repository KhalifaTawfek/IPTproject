package com.nexustradex.controller;

import com.nexustradex.model.User;
import com.nexustradex.service.TradeService;
import com.nexustradex.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class DashboardMvcController {

    private final TradeService tradeService;
    private final UserService userService;

    public DashboardMvcController(TradeService tradeService, UserService userService) {
        this.tradeService = tradeService;
        this.userService = userService;
    }

    @GetMapping("/")
    public String root() {
        return "redirect:/dashboard";
    }

    @GetMapping("/dashboard")
    public String dashboard(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        User user = userService.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        model.addAttribute("user", user);
        model.addAttribute("summary", tradeService.getDashboardSummary(user.getAgent().getId()));
        return "dashboard/index";
    }

    @GetMapping("/trades")
    public String trades(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        User user = userService.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        model.addAttribute("user", user);
        model.addAttribute("trades", tradeService.getTradesForAgent(user.getAgent().getId()));
        // H1 - Visibility of System Status: show agent status on all pages
        if (user.getAgent() != null) {
            model.addAttribute("agentStatus", user.getAgent().getStatus().toString());
        }
        return "dashboard/trades";
    }

    @GetMapping("/agent/settings")
    public String agentSettings(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        User user = userService.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));
        model.addAttribute("user", user);
        model.addAttribute("agent", user.getAgent());
        return "agent/settings";
    }

    @PostMapping("/agent/settings")
    public String saveAgentSettings(@AuthenticationPrincipal UserDetails userDetails,
                                    @RequestParam String apiKey,
                                    @RequestParam(defaultValue = "false") boolean requiresApproval,
                                    RedirectAttributes redirectAttributes) {
        userService.updateAgentApiKey(userDetails.getUsername(), apiKey, requiresApproval);
        redirectAttributes.addFlashAttribute("message", "Agent settings updated successfully.");
        return "redirect:/agent/settings";
    }

    @PostMapping("/trades/{tradeId}/approve")
    public String approveTrade(@PathVariable Long tradeId, RedirectAttributes redirectAttributes) {
        tradeService.approveTrade(tradeId);
        redirectAttributes.addFlashAttribute("message", "Trade approved.");
        return "redirect:/dashboard";
    }

    @PostMapping("/trades/{tradeId}/reject")
    public String rejectTrade(@PathVariable Long tradeId, RedirectAttributes redirectAttributes) {
        tradeService.rejectTrade(tradeId);
        redirectAttributes.addFlashAttribute("message", "Trade rejected.");
        return "redirect:/dashboard";
    }
}
