package com.backend.dev.InventoryService.dto;

import lombok.Data;

@Data
public class ProductDto {
    private Long id;

    private String name;

    private String description;

    private Double totalPrice;

    private Integer quantity; 
}
