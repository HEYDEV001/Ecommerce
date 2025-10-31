package com.backend.dev.OrderService.service;


import com.backend.dev.OrderService.dto.OrdersRequestDto;
import com.backend.dev.OrderService.entity.Orders;
import com.backend.dev.OrderService.exceptions.ResourceNotFoundException;
import com.backend.dev.OrderService.repository.OrdersRepository;
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

    public List<OrdersRequestDto> getAllOrders(){
        List<Orders> orders = ordersRepository.findAll();
        return  orders.stream().map(
                product -> modelMapper.map(product, OrdersRequestDto.class)
        ).collect(Collectors.toList());
    }

    public OrdersRequestDto getOrderById(Long id){
        return ordersRepository
                .findById(id)
                .map(product -> modelMapper.map(product, OrdersRequestDto.class))
                .orElseThrow(()-> new ResourceNotFoundException("Product not found"));
    }
}
