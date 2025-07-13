package com.leumas.finance.controller.response;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record DailyStatsResponse(
        int day,
        BigDecimal totalIncome,
        BigDecimal totalExpense,
        BigDecimal balance
) {
}
