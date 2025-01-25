package com.leumas.finance.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class IncomeDTO {

    private Long id;
    private String description;
    private Double amount;
    private LocalDate date;
}
