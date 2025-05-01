package com.leumas.finance.kafka.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Builder
public record TransactionalEvent (
    Long userId,
    String type,
    BigDecimal amount,
    String category,
    Integer year,
    Integer month
){}
