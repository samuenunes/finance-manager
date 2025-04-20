package com.leumas.finance.config;

import lombok.Builder;

@Builder
public record JWTUserData(Long id, String email, String role) {
}
