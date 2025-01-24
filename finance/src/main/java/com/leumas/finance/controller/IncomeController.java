package com.leumas.finance.controller;

import com.leumas.finance.entity.Income;
import com.leumas.finance.service.IncomeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/income")
public class IncomeController {
    private final IncomeService incomeService;

    public IncomeController(IncomeService incomeService) {
        this.incomeService = incomeService;
    }

    @GetMapping
    public ResponseEntity<List<Income>> findAll(){
        return ResponseEntity.ok(incomeService.findAll());
    }

    @PostMapping
    public ResponseEntity<Income> save(@RequestBody Income income){
        return ResponseEntity.ok(incomeService.save(income));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Income> delete(@PathVariable Long id){
        incomeService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
