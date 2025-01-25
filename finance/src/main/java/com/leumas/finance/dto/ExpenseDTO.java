package com.leumas.finance.dto;

import lombok.Data;
import java.sql.Date;

@Data
public class ExpenseDTO {

    private Long id;
    private String description;
    private Double amount;
    private String type;
    private Date date;
}

