package com.leumas.finance.controller.response;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record BalanceHistoryResponse(
        String label,
        BigDecimal balance
) {
}
