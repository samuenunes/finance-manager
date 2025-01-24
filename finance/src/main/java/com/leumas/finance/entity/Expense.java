package com.leumas.finance.entity;

import com.leumas.finance.entity.enums.ExpenseType;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;

@Entity
@Data
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;
    private Double amount;

    @Enumerated(EnumType.STRING)
    private ExpenseType type;

    private Date date;
}
