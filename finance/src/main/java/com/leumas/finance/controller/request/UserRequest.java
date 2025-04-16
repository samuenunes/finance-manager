package com.leumas.finance.controller.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserRequest(
        @NotBlank(message = "Nome do usuário é obrigatório")
        String name,
        @NotBlank(message = "Email do usuário é obrigatório")
        //@Email()
        String email,
        @NotBlank(message = "Senha é obrigatória")
        String password
) {
}
