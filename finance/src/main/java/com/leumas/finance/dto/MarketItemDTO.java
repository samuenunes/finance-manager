package com.leumas.finance.dto;

import lombok.Data;

@Data
public class MarketItemDTO {

    private Long id;
    private String description;
    private Double unitPrice;
    private Integer quantity;
    private Double totalPrice;
}
