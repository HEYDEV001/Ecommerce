package com.backend.dev.OrderService.clients;

import com.backend.dev.OrderService.advice.ApiResponse;
import com.backend.dev.OrderService.dto.OrdersRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "InventoryService",path = "/inventory")
public interface InventoryOpenFeignClient {
    @PutMapping("/products/reduceStocks")
    ApiResponse<Double> reduceStocks(@RequestBody OrdersRequestDto ordersRequestDto);
}
