package com.backend.dev.InventoryService.dto;

import lombok.Data;

import java.util.List;

@Data
public class OrderRequestItemDto {
    private Long productId;
    private Integer quantity;
}
