package com.backend.dev.OrderService.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class OrdersRequestDto {
    private Long id;
    private List<OrdersRequestItemDto> items;
    private BigDecimal totalQuantity;
}
