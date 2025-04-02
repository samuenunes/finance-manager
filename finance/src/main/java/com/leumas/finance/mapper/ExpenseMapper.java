package com.leumas.finance.mapper;

import com.leumas.finance.controller.request.ExpenseRequest;
import com.leumas.finance.controller.response.ExpenseResponse;
import com.leumas.finance.entity.Expense;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ExpenseMapper {
    public static Expense toExpense(ExpenseRequest expenseRequest) {
        return Expense
                .builder()
                .description(expenseRequest.description())
                .amount(expenseRequest.amount())
                .date(expenseRequest.date())
                .type(expenseRequest.type())
                .build();
    }

    public static ExpenseResponse toExpenseResponse(Expense expense) {
        return ExpenseResponse
                .builder()
                .id(expense.getId())
                .description(expense.getDescription())
                .amount(expense.getAmount())
                .date(expense.getDate())
                .type(expense.getType())
                .build();
    }


}
