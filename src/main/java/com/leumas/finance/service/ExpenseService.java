package com.leumas.finance.service;

import com.leumas.finance.controller.request.ExpenseRequest;
import com.leumas.finance.controller.response.ExpenseResponse;
import com.leumas.finance.entity.Expense;
import com.leumas.finance.entity.ExpenseCategory;
import com.leumas.finance.kafka.event.TransactionalEvent;
import com.leumas.finance.kafka.producer.UserStatisticsProducer;
import com.leumas.finance.mapper.ExpenseMapper;
import com.leumas.finance.mapper.KafkaEventMapper;
import com.leumas.finance.repository.ExpenseCategoryRepository;
import com.leumas.finance.repository.ExpenseRepository;
import jakarta.persistence.EntityNotFoundException;
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
        sendCreateExpenseEvent(newExpense);
        return ExpenseMapper.toExpenseResponse(newExpense);
    }

    public Optional<ExpenseResponse> update(Long id, ExpenseRequest expenseNew) {
        return expenseRepository.findById(id).map(expense -> {
            Expense expenseOld = ExpenseMapper.clone(expense);
            ExpenseMapper.mapExpense(expense, expenseNew, findCategory(expenseNew.category()));
            Expense updatedExpense = expenseRepository.save(expense);
            sendUpdateExpenseEvent(expenseOld, updatedExpense);
            return ExpenseMapper.toExpenseResponse(updatedExpense);
        });
    }
    
    private ExpenseCategory findCategory(Long id) {
        return expenseCategoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada"));
    }

    public Optional<Expense> findById(Long id) {
        return expenseRepository.findById(id);
    }

    public void deleteById(Long id) {
        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Registro não encontrado."));
        expenseRepository.delete(expense);
        sendDeleteExpenseEvent(expense);
    }

    private void sendCreateExpenseEvent(Expense expense) {
        userStatisticsProducer.sendCreateEvent(KafkaEventMapper.toTransactionalEvent(expense));
    }

    private void sendUpdateExpenseEvent(Expense expenseOld, Expense expenseNew) {
        userStatisticsProducer.sendUpdateEvent(KafkaEventMapper.toTransactionalUpdateEvent(expenseOld, expenseNew));
    }

    private void sendDeleteExpenseEvent(Expense expense) {
        userStatisticsProducer.sendDeleteEvent(KafkaEventMapper.toTransactionalEvent(expense));
    }
}
