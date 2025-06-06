package com.leumas.finance.controller.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ExpenseRequest(
        @NotBlank(message = "A descrição da despesa é obrigatória")
        String description,

        @NotNull(message = "O valor da despesa é obrigatório")
        @DecimalMin(value = "0.1", message = "O valor da despesa deve ser maior que 0.")
        BigDecimal amount,

        @NotNull(message = "A categoria da despesa é obrigatória")
        Long category,

        @NotNull(message = "A data da despesa é obrigatória")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
        LocalDate date) {
}
