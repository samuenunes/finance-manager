package com.leumas.finance.controller.response;

import lombok.Builder;

@Builder
public record ExpenseCategoryResponse(
        Long id,
        String name,
        boolean fixed,
        boolean essential
) {
}
