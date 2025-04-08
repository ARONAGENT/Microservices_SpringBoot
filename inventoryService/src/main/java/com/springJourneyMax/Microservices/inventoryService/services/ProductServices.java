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
        Double totalprice=0.0;
        for(OrderRequestItemDTO orderRequestItemDTO:orderRequestDTO.getOrderItems()){
                Long prodId=orderRequestItemDTO.getProdId();
                Integer quantity=orderRequestItemDTO.getQuantity();

                Product product=productRepository.findById(prodId).orElseThrow(
                        ()-> new RuntimeException("Id not Found "+prodId)
                );
                if(product.getStock()<quantity){
                    throw new RuntimeException("product cannot full filled your request");
                }
                product.setStock(product.getStock()-quantity);
                productRepository.save(product);
                totalprice+=product.getPrice()*quantity;
        }
        return totalprice;
    }
}
