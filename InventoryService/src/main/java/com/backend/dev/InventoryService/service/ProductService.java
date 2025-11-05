package com.backend.dev.InventoryService.service;

import com.backend.dev.InventoryService.dto.OrderRequestDto;
import com.backend.dev.InventoryService.dto.OrderRequestItemDto;
import com.backend.dev.InventoryService.dto.ProductDto;
import com.backend.dev.InventoryService.entity.Product;
import com.backend.dev.InventoryService.exceptions.ResourceNotFoundException;
import com.backend.dev.InventoryService.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {



    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    public List<ProductDto> getAllInventory(){
        List<Product> productEntity = productRepository.findAll();
        return  productEntity.stream().map(
                product -> modelMapper.map(product, ProductDto.class)
        ).collect(Collectors.toList());
    }

    public ProductDto getProductById(Long id){
       return productRepository
               .findById(id)
               .map(product -> modelMapper.map(product, ProductDto.class))
               .orElseThrow(()-> new ResourceNotFoundException("Product not found"));
    }

    @Transactional
    public Double reduceStocks(OrderRequestDto orderRequestDto) {
        log.info("reducing Stocks ");
        Double totalPrice = 0.0;
        for (OrderRequestItemDto orderRequestItemDto : orderRequestDto.getItems()) {
            Long productId = orderRequestItemDto.getProductId();
            Integer quantity = orderRequestItemDto.getQuantity();
        Product product = productRepository.findById(productId).orElseThrow(
                () -> new ResourceNotFoundException("Product not found with id: " + productId)
        );
        if(product.getQuantity()< quantity){
            throw new ResourceNotFoundException("Order for this item can not be full filled");
        }
        product.setQuantity(product.getQuantity() - quantity);
        productRepository.save(product);
        totalPrice += quantity * product.getTotalPrice();
    }
        return totalPrice;
    }
}
