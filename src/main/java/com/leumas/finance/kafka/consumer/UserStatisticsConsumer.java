package com.leumas.finance.kafka.consumer;

import com.leumas.finance.entity.UserStatistics;
import com.leumas.finance.kafka.event.TransactionalEvent;
import com.leumas.finance.repository.UserStatisticsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserStatisticsConsumer {

    private final UserStatisticsRepository repository;

    @KafkaListener(topics = "transactional-events", groupId = "finance-manager")
    public void consume(TransactionalEvent event) {
        log.info("Received transaction event: {}", event);
        UserStatistics stats = repository.findByUserIdAndYearAndMonth(event.userId(), event.year(), event.month())
                .orElseGet(() -> {
                    return UserStatistics
                            .builder()
                            .userId(event.userId())
                            .year(event.year())
                            .month(event.month())
                            .totalExpenses(BigDecimal.ZERO)
                            .totalIncomes(BigDecimal.ZERO)
                            .incomesByCategory(new HashMap<>())
                            .expensesByCategory(new HashMap<>())
                            .totalTransactions(0)
                            .build();
                });

        BigDecimal currentTotal = event.type().equalsIgnoreCase("expense")
                ? stats.getTotalExpenses()
                : stats.getTotalIncomes();

        BigDecimal updatedTotal = currentTotal.add(event.amount());

        if (event.type().equalsIgnoreCase("expense")) {
            stats.setTotalExpenses(updatedTotal);
            stats.getExpensesByCategory().merge(event.category(), event.amount(), BigDecimal::add);
        }
        if (event.type().equalsIgnoreCase("income")) {
            stats.setTotalIncomes(updatedTotal);
            stats.getIncomesByCategory().merge(event.category(), event.amount(), BigDecimal::add);
        }

        stats.setBalance(stats.getTotalIncomes().subtract(stats.getTotalExpenses()));
        stats.setTotalTransactions(stats.getTotalTransactions() + 1);
        stats.setLastUpdate(LocalDate.now());
        repository.save(stats);
    }

    @KafkaListener(topics = "transactional-events.DLQ", groupId = "finance-manager")
    public void reprocessDlq(ConsumerRecord<String, TransactionalEvent> record) {
        log.warn("Reprocessando mensagem da DLQ: {}", record.value());
        //regras a implementar ...
    }
}
