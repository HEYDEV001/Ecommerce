package com.backend.dev.OrderService.service;


import com.backend.dev.OrderService.clients.InventoryOpenFeignClient;
import com.backend.dev.OrderService.dto.OrdersRequestDto;
import com.backend.dev.OrderService.entity.OrderItem;
import com.backend.dev.OrderService.entity.Orders;
import com.backend.dev.OrderService.entity.enums.OrderStatus;
import com.backend.dev.OrderService.exceptions.ResourceNotFoundException;
import com.backend.dev.OrderService.repository.OrdersRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrdersService {

    private final OrdersRepository ordersRepository;
    private final ModelMapper modelMapper;
    private final InventoryOpenFeignClient inventoryOpenFeignClient;

    public List<OrdersRequestDto> getAllOrders() {
        List<Orders> orders = ordersRepository.findAll();
        return orders.stream().map(
                product -> modelMapper.map(product, OrdersRequestDto.class)
        ).collect(Collectors.toList());
    }

    public OrdersRequestDto getOrderById(Long id) {
        return ordersRepository
                .findById(id)
                .map(product -> modelMapper.map(product, OrdersRequestDto.class))
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
    }

    //    @Retry(name="inventoryRetry", fallbackMethod = "createOrderFallBack")
    @CircuitBreaker(name = "inventoryCircuitBreaker", fallbackMethod = "createOrderFallBack")
//    @RateLimiter(name = "inventoryRateLimiter", fallbackMethod = "createOrderFallBack")
    public OrdersRequestDto createOrder(OrdersRequestDto ordersRequestDto) {
        log.info("Create method is calling ");
        Double totalPrice = inventoryOpenFeignClient.reduceStocks(ordersRequestDto).getData();
        Orders orders = modelMapper.map(ordersRequestDto, Orders.class);

        for (OrderItem orderItem : orders.getItems()) {
            orderItem.setOrder(orders);
        }
        orders.setTotalPrice(totalPrice);
        orders.setOrderStatus(OrderStatus.APPROVED);
        Orders savedOrder = ordersRepository.save(orders);
        return modelMapper.map(savedOrder, OrdersRequestDto.class);
    }

    public OrdersRequestDto createOrderFallBack(OrdersRequestDto ordersRequestDto, Throwable throwable) {
        log.error("fallback occurred :{}", throwable.getMessage());
        return new OrdersRequestDto();
    }
}
