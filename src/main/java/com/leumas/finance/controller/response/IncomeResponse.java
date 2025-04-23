package com.leumas.finance.controller.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
public record IncomeResponse(Long id,
                             String description,
                             BigDecimal amount,
                             @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
                             LocalDate date) {
}
