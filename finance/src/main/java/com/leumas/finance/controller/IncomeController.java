package com.leumas.finance.controller;

import com.leumas.finance.controller.request.IncomeRequest;
import com.leumas.finance.controller.response.IncomeResponse;
import com.leumas.finance.entity.Income;
import com.leumas.finance.mapper.IncomeMapper;
import com.leumas.finance.service.IncomeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/income")
@RequiredArgsConstructor
public class IncomeController {

    private final IncomeService incomeService;

    @GetMapping
    public ResponseEntity<List<IncomeResponse>> findAll(){
        return ResponseEntity.ok(incomeService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<IncomeResponse> findIncomeById(@PathVariable Long id){
        return incomeService.findById(id)
                .map(income -> ResponseEntity.ok(IncomeMapper.toResponse(income)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<IncomeResponse> save(@Valid @RequestBody IncomeRequest income){
        return ResponseEntity.status(HttpStatus.CREATED).body(incomeService.save(income));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Income> delete(@PathVariable Long id){
        incomeService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
