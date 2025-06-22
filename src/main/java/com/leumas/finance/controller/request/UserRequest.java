package com.leumas.finance.controller.request;

import com.leumas.finance.entity.enums.Role;
import jakarta.validation.constraints.*;

public record UserRequest(
        @NotBlank(message = "Nome do usuário é obrigatório")
        String name,

        @NotBlank(message = "Email do usuário é obrigatório")
        @Email(message = "Email inválido")
        String email,

        @NotBlank(message = "CPF é obrigatório")
        @Size(min = 11, message = "CPF Inválido")
        String cpf,

        @NotBlank(message = "Senha é obrigatória")
        String password
) {
}
