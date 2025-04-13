package com.leumas.finance.mapper;

import com.leumas.finance.controller.request.IncomeRequest;
import com.leumas.finance.controller.response.IncomeResponse;
import com.leumas.finance.entity.Income;
import lombok.experimental.UtilityClass;

@UtilityClass
public class IncomeMapper {

    public static Income toIncome(IncomeRequest request) {
        return Income
                .builder()
                .description(request.description())
                .amount(request.amount())
                .date(request.date())
                .build();
    }

    public static IncomeResponse toResponse(Income income) {
        return IncomeResponse
                .builder()
                .id(income.getId())
                .description(income.getDescription())
                .amount(income.getAmount())
                .date(income.getDate())
                .build();
    }
}
