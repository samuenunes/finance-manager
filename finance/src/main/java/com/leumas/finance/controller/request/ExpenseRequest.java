package com.leumas.finance.controller.request;

import com.leumas.finance.entity.enums.ExpenseType;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ExpenseRequest(String description, BigDecimal amount, ExpenseType type, LocalDate date) {
}
