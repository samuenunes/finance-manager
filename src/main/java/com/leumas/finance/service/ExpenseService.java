package com.leumas.finance.service;

import com.leumas.finance.controller.request.ExpenseRequest;
import com.leumas.finance.controller.response.ExpenseResponse;
import com.leumas.finance.entity.Expense;
import com.leumas.finance.entity.ExpenseCategory;
import com.leumas.finance.kafka.event.TransactionalEvent;
import com.leumas.finance.kafka.producer.UserStatisticsProducer;
import com.leumas.finance.mapper.ExpenseMapper;
import com.leumas.finance.repository.ExpenseCategoryRepository;
import com.leumas.finance.repository.ExpenseRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final ExpenseCategoryRepository expenseCategoryRepository;
    private final UserStatisticsProducer userStatisticsProducer;

    public ExpenseService(ExpenseRepository expenseRepository, ExpenseCategoryRepository expenseCategoryRepository, UserStatisticsProducer userStatisticsProducer) {
        this.expenseRepository = expenseRepository;
        this.expenseCategoryRepository = expenseCategoryRepository;
        this.userStatisticsProducer = userStatisticsProducer;
    }

    public List<ExpenseResponse> findAll() {
        return expenseRepository.findAll()
                .stream()
                .map(ExpenseMapper::toExpenseResponse)
                .toList();
    }

    public ExpenseResponse save(ExpenseRequest expense) {
        Expense newExpense = expenseRepository.save(ExpenseMapper.toExpense(expense, findCategory(expense.category())));
        sendExpenseEvent(newExpense);
        return ExpenseMapper.toExpenseResponse(newExpense);
    }

    private ExpenseCategory findCategory(Long id) {
        return expenseCategoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada"));
    }

    public Optional<Expense> findById(Long id) {
        return expenseRepository.findById(id);
    }

    public void deleteById(Long id) {
        expenseRepository.deleteById(id);
    }

    private void sendExpenseEvent(Expense expense) {
        userStatisticsProducer.sendEvent(
                new TransactionalEvent(
                        expense.getCreatedBy(),
                        "EXPENSE",
                        expense.getAmount(),
                        expense.getCategory().getName(),
                        expense.getDate().getYear(),
                        expense.getDate().getMonthValue()
                )
        );
    }
}
