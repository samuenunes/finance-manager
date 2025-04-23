package com.leumas.finance.service;

import com.leumas.finance.controller.request.ExpenseCategoryRequest;
import com.leumas.finance.controller.response.ExpenseCategoryResponse;
import com.leumas.finance.controller.response.ExpenseResponse;
import com.leumas.finance.entity.ExpenseCategory;
import com.leumas.finance.mapper.ExpenseCategoryMapper;
import com.leumas.finance.repository.ExpenseCategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExpenseCategoryService {
    private final ExpenseCategoryRepository repository;

    public ExpenseCategoryService(ExpenseCategoryRepository expenseCategoryRepository) {
        repository = expenseCategoryRepository;
    }

    public ExpenseCategoryResponse create(ExpenseCategoryRequest expenseCategoryRequest) {
        return ExpenseCategoryMapper.toExpenseCategoryResponse(
                repository.save(ExpenseCategoryMapper.toExpenseCategory(expenseCategoryRequest))
        );
    }

    public Optional<ExpenseCategoryResponse> update(Long id, ExpenseCategoryRequest request) {
        return repository.findById(id).map(expenseCategory -> {
            expenseCategory.setName(request.name());
            expenseCategory.setEssential(request.essential());
            expenseCategory.setFixed(request.fixed());
            return ExpenseCategoryMapper.toExpenseCategoryResponse(repository.save(expenseCategory));
        });
    }

    public List<ExpenseCategoryResponse> findAll() {
        return repository.findAll().stream()
                .map(ExpenseCategoryMapper::toExpenseCategoryResponse)
                .toList();
    }

    public Optional<ExpenseCategory> findById(Long id) {
        return repository.findById(id);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
