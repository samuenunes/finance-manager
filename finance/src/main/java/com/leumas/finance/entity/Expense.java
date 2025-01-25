package com.leumas.finance.entity;

import com.leumas.finance.entity.enums.ExpenseType;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;

@Entity
@Data
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Double amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ExpenseType type;

    @Column(nullable = false)
    private Date date;
}
