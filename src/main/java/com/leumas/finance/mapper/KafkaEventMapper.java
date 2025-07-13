package com.leumas.finance.mapper;

import com.leumas.finance.entity.Expense;
import com.leumas.finance.entity.Income;
import com.leumas.finance.kafka.event.TransactionalEvent;
import com.leumas.finance.kafka.event.TransactionalUpdateEvent;

public class KafkaEventMapper {

    private static final String INCOME = "INCOME";
    private static final String EXPENSE = "EXPENSE";
    public static TransactionalEvent toTransactionalEvent(Income income) {
        return new TransactionalEvent(
                income.getCreatedBy(),
                INCOME,
                income.getAmount(),
                income.getCategory().getName(),
                income.getDate().getYear(),
                income.getDate().getMonthValue()
        );
    }

    public static TransactionalEvent toTransactionalEvent(Expense expense) {
        return new TransactionalEvent(
                expense.getCreatedBy(),
                EXPENSE,
                expense.getAmount(),
                expense.getCategory().getName(),
                expense.getDate().getYear(),
                expense.getDate().getMonthValue()
        );
    }

    public static TransactionalUpdateEvent toTransactionalUpdateEvent(Income incomeOld, Income incomeNew) {
        return TransactionalUpdateEvent.builder()
                .userId(incomeNew.getCreatedBy())
                .type(INCOME)
                .oldAmount(incomeOld.getAmount())
                .oldCategory(incomeOld.getCategory().getName())
                .oldYear(incomeOld.getDate().getYear())
                .oldMonth(incomeOld.getDate().getMonthValue())
                .newAmount(incomeNew.getAmount())
                .newCategory(incomeNew.getCategory().getName())
                .newYear(incomeNew.getDate().getYear())
                .newMonth(incomeNew.getDate().getMonthValue())
                .build();
    }

    public static TransactionalUpdateEvent toTransactionalUpdateEvent(Expense expenseOld , Expense expenseNew) {
        return TransactionalUpdateEvent.builder()
                .userId(expenseNew.getCreatedBy())
                .type(EXPENSE)
                .oldAmount(expenseOld.getAmount())
                .oldCategory(expenseOld.getCategory().getName())
                .oldYear(expenseOld.getDate().getYear())
                .oldMonth(expenseOld.getDate().getMonthValue())
                .newAmount(expenseNew.getAmount())
                .newCategory(expenseNew.getCategory().getName())
                .newYear(expenseNew.getDate().getYear())
                .newMonth(expenseNew.getDate().getMonthValue())
                .build();
    }
}
