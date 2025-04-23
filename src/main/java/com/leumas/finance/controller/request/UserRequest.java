package com.leumas.finance.controller.request;

import com.leumas.finance.entity.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserRequest(
        @NotBlank(message = "Nome do usuário é obrigatório")
        String name,
        @NotBlank(message = "Email do usuário é obrigatório")
        //@Email()
        String email,
        @NotBlank(message = "Senha é obrigatória")
        String password,
        @NotNull(message = "O nível de Usuário é obrigatório")
        Role role
) {
}
