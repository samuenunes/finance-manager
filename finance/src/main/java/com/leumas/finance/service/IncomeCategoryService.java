package com.leumas.finance.service;

import com.leumas.finance.controller.request.IncomeCategoryRequest;
import com.leumas.finance.controller.response.IncomeCategoryResponse;
import com.leumas.finance.entity.IncomeCategory;
import com.leumas.finance.repository.IncomeCategoryRepository;
import com.leumas.finance.mapper.IncomeCategoryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class  IncomeCategoryService {
    private final IncomeCategoryRepository repository;

    public IncomeCategoryResponse create(IncomeCategoryRequest incomeCategoryRequest) {
        return IncomeCategoryMapper.toIncomeCategoryResponse(
                repository.save(IncomeCategoryMapper.toIncomeCategory(incomeCategoryRequest))
        );
    }

    public Optional<IncomeCategoryResponse> update(Long id, IncomeCategoryRequest request) {
        return repository.findById(id).map(incomeCategory -> {
            incomeCategory.setName(request.name());
            incomeCategory.setFixed(request.fixed());
            return IncomeCategoryMapper.toIncomeCategoryResponse(repository.save(incomeCategory));
        });
    }

    public List<IncomeCategoryResponse> findAll() {
        return repository.findAll().stream()
                .map(IncomeCategoryMapper::toIncomeCategoryResponse)
                .toList();
    }

    public Optional<IncomeCategory> findById(Long id) {
        return repository.findById(id);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
