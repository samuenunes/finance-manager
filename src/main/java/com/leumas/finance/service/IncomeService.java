package com.leumas.finance.service;

import com.leumas.finance.controller.request.IncomeRequest;
import com.leumas.finance.controller.response.IncomeResponse;
import com.leumas.finance.entity.Income;
import com.leumas.finance.entity.IncomeCategory;
import com.leumas.finance.kafka.producer.UserStatisticsProducer;
import com.leumas.finance.kafka.event.TransactionalEvent;
import com.leumas.finance.mapper.IncomeMapper;
import com.leumas.finance.repository.IncomeCategoryRepository;
import com.leumas.finance.repository.IncomeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IncomeService {
    private final IncomeRepository incomeRepository;
    private final IncomeCategoryRepository incomeCategoryRepository;
    private final UserStatisticsProducer userStatisticsProducer;

    public IncomeService(IncomeRepository incomeRepository, IncomeCategoryRepository incomeCategoryRepository, UserStatisticsProducer userStatisticsProducer) {
        this.incomeRepository = incomeRepository;
        this.incomeCategoryRepository = incomeCategoryRepository;
        this.userStatisticsProducer = userStatisticsProducer;
    }

    public List<IncomeResponse> findAll() {
        return incomeRepository.findAll()
                .stream()
                .map(IncomeMapper::toResponse)
                .toList();
    }

    public IncomeResponse save(IncomeRequest income) {
        Income newIncome =  incomeRepository.save(IncomeMapper.toIncome(income, findCategory(income.category())));
        sendIncomeEvent(newIncome);
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

    private void sendIncomeEvent(Income income){
        userStatisticsProducer.sendEvent(
                new TransactionalEvent(
                        income.getCreatedBy(),
                        "INCOME",
                        income.getAmount(),
                        income.getCategory().getName(),
                        income.getDate().getYear(),
                        income.getDate().getMonthValue()
                )
        );
    }
}
