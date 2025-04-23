package com.leumas.finance.controller.response;

import lombok.Builder;

@Builder
public record IncomeCategoryResponse(
        Long id,
        String name,
        boolean fixed
) {
}
