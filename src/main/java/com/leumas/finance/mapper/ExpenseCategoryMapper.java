package com.leumas.finance.mapper;

import com.leumas.finance.controller.request.ExpenseCategoryRequest;
import com.leumas.finance.controller.response.ExpenseCategoryResponse;
import com.leumas.finance.entity.ExpenseCategory;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ExpenseCategoryMapper {

    public static ExpenseCategory toExpenseCategory(ExpenseCategoryRequest request) {
        return ExpenseCategory
                .builder()
                .name(request.name())
                .fixed(request.fixed())
                .essential(request.essential())
                .build();
    }

    public static ExpenseCategoryResponse toExpenseCategoryResponse(ExpenseCategory expenseCategory) {
        return ExpenseCategoryResponse
                .builder()
                .id(expenseCategory.getId())
                .name(expenseCategory.getName())
                .fixed(expenseCategory.isFixed())
                .essential(expenseCategory.isEssential())
                .build();
    }
}
