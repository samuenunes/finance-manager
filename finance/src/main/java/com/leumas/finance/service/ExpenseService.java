package com.leumas.finance.service;

import com.leumas.finance.controller.request.ExpenseRequest;
import com.leumas.finance.controller.response.ExpenseResponse;
import com.leumas.finance.entity.Expense;
import com.leumas.finance.mapper.ExpenseMapper;
import com.leumas.finance.repository.ExpenseRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExpenseService {

    private final ExpenseRepository expenseRepository;

    public ExpenseService(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    public List<ExpenseResponse> findAll() {
        return expenseRepository.findAll()
                .stream()
                .map(ExpenseMapper::toExpenseResponse)
                .toList();
    }

    public ExpenseResponse save(ExpenseRequest expense) {
        Expense newExpense = expenseRepository.save(ExpenseMapper.toExpense(expense));
        return ExpenseMapper.toExpenseResponse(newExpense);
    }

    public Optional<ExpenseResponse> findById(Long id) {
        return expenseRepository.findById(id).map(ExpenseMapper::toExpenseResponse);
    }

    public void deleteById(Long id) {
        expenseRepository.deleteById(id);
    }
}
