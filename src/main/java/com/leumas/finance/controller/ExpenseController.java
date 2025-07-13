package com.leumas.finance.controller;

import com.leumas.finance.controller.request.ExpenseRequest;
import com.leumas.finance.controller.response.ExpenseResponse;
import com.leumas.finance.entity.Expense;
import com.leumas.finance.mapper.ExpenseMapper;
import com.leumas.finance.service.ExpenseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/expense")
@RequiredArgsConstructor
public class ExpenseController {
    private final ExpenseService expenseService;

    @GetMapping
    public ResponseEntity<List<ExpenseResponse>> getAllExpenses() {
        return ResponseEntity.ok(expenseService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExpenseResponse> getExpenseById(@PathVariable Long id) {
        return expenseService.findById(id)
                .map(expense -> ResponseEntity.ok(ExpenseMapper.toExpenseResponse(expense)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ExpenseResponse> save(@Valid @RequestBody ExpenseRequest expense) {
        return ResponseEntity.status(HttpStatus.CREATED).body(expenseService.save(expense));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExpenseResponse> update(@PathVariable Long id, @Valid @RequestBody ExpenseRequest expense) {
        return expenseService.update(id, expense)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        expenseService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
