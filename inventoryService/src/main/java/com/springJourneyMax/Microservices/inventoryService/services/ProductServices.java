package com.springJourneyMax.Microservices.inventoryService.services;

import com.springJourneyMax.Microservices.inventoryService.dto.OrderRequestDTO;
import com.springJourneyMax.Microservices.inventoryService.dto.OrderRequestItemDTO;
import com.springJourneyMax.Microservices.inventoryService.dto.ProductDto;
import com.springJourneyMax.Microservices.inventoryService.entity.Product;
import com.springJourneyMax.Microservices.inventoryService.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductServices {

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    public List<ProductDto> getAllProducts(){
        log.info("Fetching All Inventory Items ....");
        List<Product> allProducts=productRepository.findAll();
        return allProducts.stream()
                .map(product -> modelMapper.map(product,ProductDto.class))
                .toList();
    }

    public ProductDto getProdById(Long id){
        log.info("Fetching Product with Id : {}",id);
        Optional<Product> getProdById=productRepository.findById(id);
        return  getProdById.map(product -> modelMapper.map(product,ProductDto.class))
                .orElseThrow(() -> new RuntimeException("Inventory Not found"));
    }

    public Double reduceStocks(OrderRequestDTO orderRequestDTO) {
        log.info("Starting reduceStocks for Id : {}", orderRequestDTO.getId());
        Double totalPrice = 0.0;
        for (OrderRequestItemDTO orderRequestItemDTO : orderRequestDTO.getOrderItems()) {
            Long prodId = orderRequestItemDTO.getProdId();
            Integer quantity = orderRequestItemDTO.getQuantity();
            log.info("Processing product ID: {}, Quantity: {}", prodId, quantity);
            Product product = productRepository.findById(prodId).orElseThrow(() -> new RuntimeException("ID not found: " + prodId));
            if (product.getStock() < quantity) {
                log.warn("Insufficient stock for product ID: {}. Requested: {}, Available: {}",
                        prodId, quantity, product.getStock());
                throw new RuntimeException("Product cannot fulfill your request");
            }
            product.setStock(product.getStock() - quantity);
            productRepository.save(product);
            log.info("Updated stock for product ID: {}. Remaining stock: {}", prodId, product.getStock());
            totalPrice += product.getPrice() * quantity;
            log.info("Updated total price: {}", totalPrice);
        }
        log.info("Completed reduceStocks. Total price: {}", totalPrice);
        return totalPrice;
    }

    public void increaseStocks(OrderRequestDTO orderRequestDTO) {
        log.info("Starting increaseStocks for Id: {}", orderRequestDTO.getId());
        for (OrderRequestItemDTO orderRequestItemDTO : orderRequestDTO.getOrderItems()) {
            Long prodId = orderRequestItemDTO.getProdId();
            Integer quantity = orderRequestItemDTO.getQuantity();
            Product product = productRepository.findById(prodId).orElseThrow(() -> new RuntimeException("ID not found: " + prodId));
            product.setStock(product.getStock() + quantity);
            productRepository.save(product);
            log.info("Updated stock for product ID: {}. New stock: {}", prodId, product.getStock());
        }
        log.info("Completed increaseStocks operation.");
    }
}
