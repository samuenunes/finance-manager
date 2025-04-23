package com.leumas.finance.service;

import com.leumas.finance.controller.request.IncomeRequest;
import com.leumas.finance.controller.response.IncomeResponse;
import com.leumas.finance.entity.Income;
import com.leumas.finance.mapper.IncomeMapper;
import com.leumas.finance.repository.IncomeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class IncomeService {
    private final IncomeRepository incomeRepository;

    public IncomeService(IncomeRepository incomeRepository) {
        this.incomeRepository = incomeRepository;
    }

    public List<IncomeResponse> findAll() {
        return incomeRepository.findAll()
                .stream()
                .map(IncomeMapper::toResponse)
                .toList();
    }

    public IncomeResponse save(IncomeRequest income) {
        Income newIncome =  incomeRepository.save(IncomeMapper.toIncome(income));
        return IncomeMapper.toResponse(newIncome);
    }

    public Optional<Income> findById(Long id) {
        return incomeRepository.findById(id);
    }

    public void deleteById(Long id) {
        incomeRepository.deleteById(id);
    }
}
