package com.backend.dev.OrderService.dto;

import lombok.Data;

@Data
public class OrdersRequestItemDto {
private Long id;
private Long productId;
private Integer quantity;
}
