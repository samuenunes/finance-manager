package com.leumas.finance.mapper;

import com.leumas.finance.controller.request.IncomeCategoryRequest;
import com.leumas.finance.controller.response.IncomeCategoryResponse;
import com.leumas.finance.entity.IncomeCategory;
import lombok.experimental.UtilityClass;

@UtilityClass
public class IncomeCategoryMapper {

    public static IncomeCategory toIncomeCategory(IncomeCategoryRequest request) {
        return IncomeCategory
                .builder()
                .name(request.name())
                .fixed(request.fixed())
                .build();
    }

    public static IncomeCategoryResponse toIncomeCategoryResponse(IncomeCategory incomeCategory) {
        return IncomeCategoryResponse
                .builder()
                .id(incomeCategory.getId())
                .name(incomeCategory.getName())
                .fixed(incomeCategory.isFixed())
                .build();
    }
}
