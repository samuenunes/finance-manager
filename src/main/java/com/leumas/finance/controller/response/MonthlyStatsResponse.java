package com.leumas.finance.controller.response;

import java.math.BigDecimal;

public record MonthlyStatsResponse(
        int year,
        int month,
        BigDecimal totalIncomes,
        BigDecimal totalExpenses
) {}
