package com.leumas.finance.service;

import com.leumas.finance.controller.response.BalanceHistoryResponse;
import com.leumas.finance.controller.response.DailyStatsResponse;
import com.leumas.finance.controller.response.DashboardResponse;
import com.leumas.finance.controller.response.MonthlyStatsResponse;
import com.leumas.finance.entity.UserStatistics;
import com.leumas.finance.repository.UserStatisticsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DashboardService {
    private final UserStatisticsRepository repository;

    public List<MonthlyStatsResponse> getMonthlyStats(int year) {
        List<UserStatistics> statsList = repository.findByYear(year);
        return statsList.stream()
                .sorted(Comparator.comparing(UserStatistics::getMonth))
                .map(stat -> new MonthlyStatsResponse(
                        stat.getYear(),
                        stat.getMonth(),
                        stat.getTotalIncomes(),
                        stat.getTotalExpenses()
                )).toList();
    }

    public DashboardResponse getCurrentMonthStats() {
        LocalDate now = LocalDate.now();
        UserStatistics stats = repository.findByYearAndMonth(now.getYear(), now.getMonthValue())
                .orElseThrow(() -> new RuntimeException("No statistics found"));

        return DashboardResponse.builder()
                .totalIncome(stats.getTotalIncomes())
                .totalExpense(stats.getTotalExpenses())
                .balance(stats.getBalance())
                .monthlyIncome(stats.getTotalIncomes())
                .monthlyExpenses(stats.getTotalExpenses())
                .monthlyBalance(stats.getBalance())
                .incomesByCategory(stats.getIncomesByCategory())
                .expensesByCategory(stats.getExpensesByCategory())
                .totalTransactions(stats.getTotalTransactions())
                .lastUpdate(stats.getLastUpdate().toString())
                .build();
    }

    public List<BalanceHistoryResponse> getBalanceHistory(int months) {
        LocalDate now = LocalDate.now();
        List<BalanceHistoryResponse> history = new ArrayList<>();

        for (int i = months - 1; i >= 0; i--) {
            LocalDate date = now.minusMonths(i);
            repository.findByYearAndMonth(date.getYear(), date.getMonthValue()).ifPresent(stats -> {
                String label = String.format("%s/%02d", date.getMonth().name().substring(0, 3), date.getYear() % 100);
                history.add(new BalanceHistoryResponse(label, stats.getBalance()));
            });
        }
        return history;
    }

    public List<DailyStatsResponse> getDailyStats(int year, int month) {
        List<DailyStatsResponse> result = new ArrayList<>();
        // Em uma futura versão, usar uma nova tabela agregada por dia (por enquanto estático)
        return result;
    }
}
