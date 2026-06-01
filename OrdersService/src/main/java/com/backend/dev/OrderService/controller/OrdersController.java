package com.backend.dev.OrderService.controller;


import com.backend.dev.OrderService.clients.InventoryOpenFeignClient;
import com.backend.dev.OrderService.dto.OrdersRequestDto;
import com.backend.dev.OrderService.service.OrdersService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(path ="/core")
@RefreshScope
public class OrdersController {

    @Value("${my.variable}")
    private String myVariable;

    private final OrdersService ordersService;
    private final InventoryOpenFeignClient inventoryOpenFeignClient;

    @GetMapping(path= "/helloOrders")
    public ResponseEntity<String> helloOrders(){
        return ResponseEntity.ok("Hello from orderService with User  : "+myVariable);
    }

    @GetMapping
    public ResponseEntity<List<OrdersRequestDto>> getAllOrders(){
        List<OrdersRequestDto> ordersRequestDto = ordersService.getAllOrders();
        return ResponseEntity.ok(ordersRequestDto);
    }


    @GetMapping("/{id}")
    public ResponseEntity<OrdersRequestDto> getOrderById(@PathVariable Long id) {
        OrdersRequestDto orders = ordersService.getOrderById(id);
        return ResponseEntity.ok(orders);
    }

    @PostMapping("/createOrder")
    public ResponseEntity<OrdersRequestDto> createOrder(@RequestBody OrdersRequestDto ordersRequestDto) {
     OrdersRequestDto order =  ordersService.createOrder(ordersRequestDto);
     return ResponseEntity.ok(order);

    }

}
