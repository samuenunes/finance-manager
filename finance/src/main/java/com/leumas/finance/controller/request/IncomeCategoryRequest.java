package com.leumas.finance.controller.request;

import jakarta.validation.constraints.NotBlank;

public record IncomeCategoryRequest(
        @NotBlank(message = "O nome da categoria é obrigatório.")
        String name,
        boolean fixed
) {
}
