package com.leumas.finance.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "user_statistics")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserStatistics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private Integer year;
    private Integer month;

    private BigDecimal totalExpenses = BigDecimal.ZERO;
    private BigDecimal totalIncomes = BigDecimal.ZERO;
    private BigDecimal balance = BigDecimal.ZERO;

    @ElementCollection
    @CollectionTable(name = "user_expense_category_totals", joinColumns = @JoinColumn(name = "statistics_id"))
    @MapKeyColumn(name = "category")
    @Column(name = "amount")
    private Map<String, BigDecimal> expensesByCategory = new HashMap<>();

    @ElementCollection
    @CollectionTable(name = "user_income_category_totals", joinColumns = @JoinColumn(name = "statistics_id"))
    @MapKeyColumn(name = "category")
    @Column(name = "amount")
    private Map<String, BigDecimal> incomesByCategory = new HashMap<>();

    private Integer totalTransactions = 0;

    private LocalDate lastUpdate;
}
