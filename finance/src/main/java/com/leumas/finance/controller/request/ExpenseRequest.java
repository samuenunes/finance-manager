package com.leumas.finance.controller.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.leumas.finance.entity.enums.ExpenseType;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ExpenseRequest(
        @NotEmpty(message = "Descrição obrigatória")
        @NotBlank
        String description,

        @NotNull(message = "O valor da despesa é obrigatório")
        @DecimalMin(value = "0.1", message = "O valor da despesa deve ser maior que 0.")
        BigDecimal amount,

       // @NotNull(message = "Tipo de despesa é obrigatório")
        //@NotBlank(message = "Tipo de despesa é obrigatório")
        ExpenseType type,

        //@NotEmpty(message = "Data é obrigatória")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
        LocalDate date) {
}
