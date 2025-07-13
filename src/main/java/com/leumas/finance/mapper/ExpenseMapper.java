package com.leumas.finance.mapper;

import com.leumas.finance.controller.request.ExpenseRequest;
import com.leumas.finance.controller.response.ExpenseResponse;
import com.leumas.finance.entity.Expense;
import com.leumas.finance.entity.ExpenseCategory;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ExpenseMapper {

    public static Expense toExpense(ExpenseRequest expenseRequest, ExpenseCategory category) {
        return Expense
                .builder()
                .description(expenseRequest.description())
                .amount(expenseRequest.amount())
                .date(expenseRequest.date())
                .category(category)
                .build();
    }

    public static Expense clone(Expense expense) {
        return Expense.builder()
                .id(expense.getId())
                .description(expense.getDescription())
                .amount(expense.getAmount())
                .category(expense.getCategory())
                .date(expense.getDate())
                .build();
    }

    public void mapExpense(Expense expenseTarget, ExpenseRequest expenseSource, ExpenseCategory category) {
        expenseTarget.setDescription(expenseSource.description());
        expenseTarget.setAmount(expenseSource.amount());
        expenseTarget.setCategory(category);
        expenseTarget.setDate(expenseSource.date());
    }
    
    public static ExpenseResponse toExpenseResponse(Expense expense) {
        return ExpenseResponse
                .builder()
                .id(expense.getId())
                .description(expense.getDescription())
                .amount(expense.getAmount())
                .date(expense.getDate())
                .category(ExpenseCategoryMapper.toExpenseCategoryResponse(expense.getCategory()))
                .build();
    }
}
