package com.backend.dev.InventoryService.controller;

import com.backend.dev.InventoryService.clients.OrderFeignClients;
import com.backend.dev.InventoryService.dto.OrderRequestDto;
import com.backend.dev.InventoryService.dto.ProductDto;
import com.backend.dev.InventoryService.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    private final DiscoveryClient discoveryClient;
    private final RestClient restClient;
    private final OrderFeignClients orderFeignClients;

    @GetMapping("/fetch")
    public String fetchFromOrdersService(){
//        ServiceInstance orderService =  discoveryClient.getInstances("OrderService").get(0);
//
//        return restClient
//                .get()
//                .uri(orderService.getUri() + "/orders/core/helloOrders")
//                .retrieve()
//                .body(String.class);

        return orderFeignClients.helloOrders();

    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllInventory(){
        List<ProductDto> productDtoList = productService.getAllInventory();
        return ResponseEntity.ok(productDtoList);
    }


    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long id){
        ProductDto productDto = productService.getProductById(id);
        return ResponseEntity.ok(productDto);
    }


    @PutMapping("/reduceStocks")
    public Double reduceStocks(@RequestBody OrderRequestDto orderRequestDto) {
        return productService.reduceStocks(orderRequestDto);
    }
}
