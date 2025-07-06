package com.leumas.finance.kafka.event;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record TransactionalUpdateEvent(
        Long userId,
        String type,
        BigDecimal oldAmount,
        BigDecimal newAmount,
        String oldCategory,
        String newCategory,
        int oldYear,
        int newYear,
        int oldMonth,
        int newMonth
){}

