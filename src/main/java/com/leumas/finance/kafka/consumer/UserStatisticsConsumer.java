package com.leumas.finance.kafka.consumer;

import com.leumas.finance.config.db.TenantContext;
import com.leumas.finance.entity.UserStatistics;
import com.leumas.finance.kafka.event.TransactionalEvent;
import com.leumas.finance.kafka.event.TransactionalUpdateEvent;
import com.leumas.finance.repository.UserStatisticsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserStatisticsConsumer {

    private final UserStatisticsRepository repository;

    @KafkaListener(topics = "transactional-create-events", groupId = "finance-manager")
    public void consume(TransactionalEvent event) {
        log.info("Received transaction event: {}", event);
        TenantContext.setCurrentTenant(event.userId().toString());
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
            safeMerge(stats.getExpensesByCategory(), event.category(), event.amount());
        }
        if (event.type().equalsIgnoreCase("income")) {
            stats.setTotalIncomes(updatedTotal);
            safeMerge(stats.getIncomesByCategory(), event.category(), event.amount());
        }

        stats.setBalance(stats.getTotalIncomes().subtract(stats.getTotalExpenses()));
        stats.setTotalTransactions(stats.getTotalTransactions() + 1);
        stats.setLastUpdate(LocalDate.now());
        repository.save(stats);
    }

    @KafkaListener(topics = "transactional-create-events.DLQ", groupId = "finance-manager")
    public void reprocessDlq(ConsumerRecord<String, TransactionalEvent> record) {
        log.warn("Reprocessando mensagem da DLQ: {}", record.value());
        //regras a implementar ...
    }

    @KafkaListener(topics = "transactional-update-events", groupId = "finance-manager")
    public void consumeUpdate(TransactionalUpdateEvent event) {
        log.info("Received update event: {}", event);
        TenantContext.setCurrentTenant(event.userId().toString());

        repository.findByUserIdAndYearAndMonth(event.userId(), event.oldYear(), event.oldMonth())
                .ifPresent(oldStats -> {
                    if ("INCOME".equalsIgnoreCase(event.type())) {
                        oldStats.setTotalIncomes(oldStats.getTotalIncomes().subtract(event.oldAmount()));
                        safeMerge(oldStats.getIncomesByCategory(), event.oldCategory(), event.oldAmount().negate());
                    } else {
                        oldStats.setTotalExpenses(oldStats.getTotalExpenses().subtract(event.oldAmount()));
                        safeMerge(oldStats.getExpensesByCategory(), event.oldCategory(), event.oldAmount().negate());
                    }

                    oldStats.setBalance(oldStats.getTotalIncomes().subtract(oldStats.getTotalExpenses()));
                    oldStats.setLastUpdate(LocalDate.now());
                    repository.save(oldStats);
                });

        UserStatistics newStats = repository.findByUserIdAndYearAndMonth(event.userId(), event.newYear(), event.newMonth())
                .orElseGet(() -> UserStatistics.builder()
                        .userId(event.userId())
                        .year(event.newYear())
                        .month(event.newMonth())
                        .totalExpenses(BigDecimal.ZERO)
                        .totalIncomes(BigDecimal.ZERO)
                        .incomesByCategory(new HashMap<>())
                        .expensesByCategory(new HashMap<>())
                        .totalTransactions(0)
                        .build());

        if ("INCOME".equalsIgnoreCase(event.type())) {
            newStats.setTotalIncomes(newStats.getTotalIncomes().add(event.newAmount()));
            safeMerge(newStats.getIncomesByCategory(), event.newCategory(), event.newAmount());
        } else {
            newStats.setTotalExpenses(newStats.getTotalExpenses().add(event.newAmount()));
            safeMerge(newStats.getExpensesByCategory(), event.newCategory(), event.newAmount());
        }

        newStats.setBalance(newStats.getTotalIncomes().subtract(newStats.getTotalExpenses()));
        newStats.setLastUpdate(LocalDate.now());
        repository.save(newStats);
    }

    @KafkaListener(topics = "transactional-delete-events", groupId = "finance-manager")
    public void consumeDelete(TransactionalEvent event) {
        log.info("Received delete event: {}", event);
        TenantContext.setCurrentTenant(event.userId().toString());

        repository.findByUserIdAndYearAndMonth(event.userId(), event.year(), event.month())
                .ifPresent(stats -> {
                    if (event.type().equalsIgnoreCase("INCOME")) {
                        stats.setTotalIncomes(stats.getTotalIncomes().subtract(event.amount()));
                        safeMerge(stats.getIncomesByCategory(), event.category(), event.amount().negate());
                    } else {
                        stats.setTotalExpenses(stats.getTotalExpenses().subtract(event.amount()));
                        safeMerge(stats.getExpensesByCategory(), event.category(), event.amount().negate());
                    }
                    stats.setBalance(stats.getTotalIncomes().subtract(stats.getTotalExpenses()));
                    stats.setTotalTransactions(Math.max(0, stats.getTotalTransactions() - 1));
                    stats.setLastUpdate(LocalDate.now());
                    repository.save(stats);
                });
    }


    private static void safeMerge(Map<String, BigDecimal> map, String key, BigDecimal delta) {
        map.merge(key, delta, BigDecimal::add);
        map.computeIfPresent(key, (k, v) -> v.compareTo(BigDecimal.ZERO) == 0 ? null : v);
    }

}
