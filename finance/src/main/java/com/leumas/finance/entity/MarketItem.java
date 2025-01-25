package com.leumas.finance.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class MarketItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private Double quantity;

    @Column(nullable = false)
    private Double amount;
}
