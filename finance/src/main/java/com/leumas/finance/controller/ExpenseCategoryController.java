package com.leumas.finance.controller;

import com.leumas.finance.controller.request.ExpenseCategoryRequest;
import com.leumas.finance.controller.response.ExpenseCategoryResponse;
import com.leumas.finance.mapper.ExpenseCategoryMapper;
import com.leumas.finance.service.ExpenseCategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/expense-category")
@RequiredArgsConstructor
public class ExpenseCategoryController {
    private final ExpenseCategoryService service;

    @GetMapping
    ResponseEntity<List<ExpenseCategoryResponse>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    ResponseEntity<ExpenseCategoryResponse> findById(@PathVariable Long id) {
        return service.findById(id)
                .map(expenseCategory ->
                        ResponseEntity.ok(ExpenseCategoryMapper.toExpenseCategoryResponse(expenseCategory)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    ResponseEntity<ExpenseCategoryResponse> save(@Valid @RequestBody ExpenseCategoryRequest expenseCategoryRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                service.create(expenseCategoryRequest)
        );
    }

    @PutMapping("/{id}")
    ResponseEntity<ExpenseCategoryResponse> update(@PathVariable Long id,
        @Valid @RequestBody ExpenseCategoryRequest expenseCategoryRequest
    ) {
        return service.update(id, expenseCategoryRequest)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
