package com.leumas.finance.controller.response;

import lombok.Builder;

import java.math.BigDecimal;
import java.util.Map;

@Builder
public record DashboardResponse(
        BigDecimal totalIncome,
        BigDecimal totalExpense,
        BigDecimal balance,
        BigDecimal monthlyIncome,
        BigDecimal monthlyExpenses,
        BigDecimal monthlyBalance,
        Map<String, BigDecimal> incomesByCategory,
        Map<String, BigDecimal> expensesByCategory,
        Integer totalTransactions,
        String lastUpdate
) {
}
