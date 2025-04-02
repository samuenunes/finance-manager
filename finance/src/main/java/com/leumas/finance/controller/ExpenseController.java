package com.leumas.finance.controller;

import com.leumas.finance.controller.request.ExpenseRequest;
import com.leumas.finance.controller.response.ExpenseResponse;
import com.leumas.finance.entity.Expense;
import com.leumas.finance.service.ExpenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/expense")
public class ExpenseController {
    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @GetMapping
    public ResponseEntity<List<ExpenseResponse>> getAllExpenses() {
        return ResponseEntity.ok(expenseService.findAll());
    }

    @PostMapping
    public ResponseEntity<ExpenseResponse> save(@RequestBody ExpenseRequest expense) {
        return ResponseEntity.ok(expenseService.save(expense));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        expenseService.deleteById(id);
        //return ResponseEntity.noContent().build();
    }
}
