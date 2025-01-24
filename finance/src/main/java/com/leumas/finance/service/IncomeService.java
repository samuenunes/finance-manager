package com.leumas.finance.service;

import com.leumas.finance.entity.Income;
import com.leumas.finance.repository.IncomeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IncomeService {
    private final IncomeRepository incomeRepository;

    public IncomeService(IncomeRepository incomeRepository) {
        this.incomeRepository = incomeRepository;
    }

    public List<Income> findAll() {
        return incomeRepository.findAll();
    }

    public Income save(Income income) {
        return incomeRepository.save(income);
    }

    public Income findById(Long id) {
        return incomeRepository.findById(id).orElse(null);
    }

    public void deleteById(Long id) {
        incomeRepository.deleteById(id);
    }
}
