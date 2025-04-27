package com.leumas.finance.service;

import com.leumas.finance.controller.request.IncomeRequest;
import com.leumas.finance.controller.response.IncomeResponse;
import com.leumas.finance.entity.Income;
import com.leumas.finance.entity.IncomeCategory;
import com.leumas.finance.mapper.IncomeMapper;
import com.leumas.finance.repository.IncomeCategoryRepository;
import com.leumas.finance.repository.IncomeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class IncomeService {
    private final IncomeRepository incomeRepository;
    private final IncomeCategoryRepository incomeCategoryRepository;

    public IncomeService(IncomeRepository incomeRepository, IncomeCategoryRepository incomeCategoryRepository) {
        this.incomeRepository = incomeRepository;
        this.incomeCategoryRepository = incomeCategoryRepository;
    }

    public List<IncomeResponse> findAll() {
        return incomeRepository.findAll()
                .stream()
                .map(IncomeMapper::toResponse)
                .toList();
    }

    public IncomeResponse save(IncomeRequest income) {
        Income newIncome =  incomeRepository.save(IncomeMapper.toIncome(income, findCategory(income.category())));
        return IncomeMapper.toResponse(newIncome);
    }

    private IncomeCategory findCategory(Long id) {
        return incomeCategoryRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Categoria de receita não encontrada.")
        );
    }

    public Optional<Income> findById(Long id) {
        return incomeRepository.findById(id);
    }

    public void deleteById(Long id) {
        incomeRepository.deleteById(id);
    }
}
