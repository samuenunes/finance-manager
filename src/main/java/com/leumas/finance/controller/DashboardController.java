package com.leumas.finance.controller;

import com.leumas.finance.controller.response.BalanceHistoryResponse;
import com.leumas.finance.controller.response.DailyStatsResponse;
import com.leumas.finance.controller.response.DashboardResponse;
import com.leumas.finance.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.YearMonth;
import java.util.List;

@RestController
@RequestMapping("/api/v1/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping
    public DashboardResponse getCurrentStats() {
        return dashboardService.getCurrentMonthStats();
    }

    @GetMapping("/balance-history")
    public List<BalanceHistoryResponse> getBalanceHistory(@RequestParam(defaultValue = "6") int months) {
        return dashboardService.getBalanceHistory(months);
    }

    @GetMapping("/daily")
    public List<DailyStatsResponse> getDailyStats(@RequestParam int year, @RequestParam int month) {
        return dashboardService.getDailyStats(year, month);
    }
}
