package com.leumas.finance.mapper;

import com.leumas.finance.controller.request.IncomeRequest;
import com.leumas.finance.controller.response.IncomeResponse;
import com.leumas.finance.entity.Income;
import com.leumas.finance.entity.IncomeCategory;
import lombok.experimental.UtilityClass;

@UtilityClass
public class IncomeMapper {

    public static Income toIncome(IncomeRequest request, IncomeCategory category) {
        return Income
                .builder()
                .description(request.description())
                .amount(request.amount())
                .category(category)
                .date(request.date())
                .build();
    }

    public static Income clone(Income income) {
        return Income.builder()
                .id(income.getId())
                .description(income.getDescription())
                .amount(income.getAmount())
                .category(income.getCategory())
                .date(income.getDate())
                .build();
    }

    public void mapIncome(Income incomeTarget, IncomeRequest incomeSource, IncomeCategory category) {
        incomeTarget.setDescription(incomeSource.description());
        incomeTarget.setAmount(incomeSource.amount());
        incomeTarget.setCategory(category);
        incomeTarget.setDate(incomeSource.date());
    }

    public static IncomeResponse toResponse(Income income) {
        return IncomeResponse
                .builder()
                .id(income.getId())
                .description(income.getDescription())
                .amount(income.getAmount())
                .date(income.getDate())
                .category(IncomeCategoryMapper.toIncomeCategoryResponse(income.getCategory()))
                .build();
    }
}
