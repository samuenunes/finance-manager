package com.leumas.finance.controller.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

public record IncomeRequest(
        @NotBlank(message = "A descrição da receita é obrigatória")
        String description,

        @NotNull(message = "O valor da receita é obrigatório")
        @DecimalMin(value = "0.1", message = "O valor da receita deve ser maior que 0.")
        BigDecimal amount,

        @NotNull(message = "A data da receita é obrigatória")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
        LocalDate date) {
}
