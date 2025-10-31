package com.backend.dev.OrderService.controller;


import com.backend.dev.OrderService.dto.OrdersRequestDto;
import com.backend.dev.OrderService.service.OrdersService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrdersController {

    private final OrdersService ordersService;


    @GetMapping
    public ResponseEntity<List<OrdersRequestDto>> getAllInventory(){
        List<OrdersRequestDto> ordersRequestDto = ordersService.getAllOrders();
        return ResponseEntity.ok(ordersRequestDto);
    }


    @GetMapping("/{id}")
    public ResponseEntity<OrdersRequestDto> getProductById(@PathVariable Long id) {
        OrdersRequestDto orders = ordersService.getOrderById(id);
        return ResponseEntity.ok(orders);
    }

}
