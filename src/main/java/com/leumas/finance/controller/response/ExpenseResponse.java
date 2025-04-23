package com.leumas.finance.controller.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.leumas.finance.entity.ExpenseCategory;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
public record ExpenseResponse (Long id,
                               String description,
                               BigDecimal amount,
                               ExpenseCategoryResponse category,
                               @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
                               LocalDate date) {

}
