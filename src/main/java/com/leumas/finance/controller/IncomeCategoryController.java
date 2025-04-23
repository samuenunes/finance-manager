package com.leumas.finance.controller;

import com.leumas.finance.controller.request.IncomeCategoryRequest;
import com.leumas.finance.controller.response.IncomeCategoryResponse;
import com.leumas.finance.mapper.IncomeCategoryMapper;
import com.leumas.finance.service.IncomeCategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/income-category")
@RequiredArgsConstructor
public class IncomeCategoryController {
    private final IncomeCategoryService service;

    @GetMapping
    ResponseEntity<List<IncomeCategoryResponse>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    ResponseEntity<IncomeCategoryResponse> findById(@PathVariable Long id) {
        return service.findById(id)
                .map(incomeCategory ->
                        ResponseEntity.ok(IncomeCategoryMapper.toIncomeCategoryResponse(incomeCategory)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    ResponseEntity<IncomeCategoryResponse> save(@Valid @RequestBody IncomeCategoryRequest incomeCategoryRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                service.create(incomeCategoryRequest)
        );
    }

    @PutMapping("/{id}")
    ResponseEntity<IncomeCategoryResponse> update(@PathVariable Long id,
        @Valid @RequestBody IncomeCategoryRequest incomeCategoryRequest
    ) {
        return service.update(id, incomeCategoryRequest)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
