package com.backend.dev.InventoryService.service;

import com.backend.dev.InventoryService.dto.ProductDto;
import com.backend.dev.InventoryService.entity.Product;
import com.backend.dev.InventoryService.exceptions.ResourceNotFoundException;
import com.backend.dev.InventoryService.repository.ProductRepository;
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
}
