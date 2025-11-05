package com.backend.dev.InventoryService.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "OrderService",path = "/orders")
public interface OrderFeignClients {

    @GetMapping("/core/helloOrders")
    String helloOrders();
}
