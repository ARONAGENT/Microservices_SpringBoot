package com.springJourneyMax.Microservices.inventoryService.controllers;

import com.springJourneyMax.Microservices.inventoryService.dto.ProductDto;
import com.springJourneyMax.Microservices.inventoryService.services.ProductServices;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ProductServices productServices;
    private final DiscoveryClient discoveryClient;
    private final RestClient restClient;

    @GetMapping("/FetchingOrders")
    public String fetchOrdersFromOrderService(HttpServletRequest httpServletRequest){
        log.info(httpServletRequest.getHeader("X-Request-Id"));
        ServiceInstance orderService=discoveryClient
                .getInstances("orderService").getFirst();
        return restClient.get()
                .uri(orderService.getUri()+"/orders/core/HelloOrder")
                .retrieve()
                .body(String.class);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ProductDto>> allProducts(){
        List<ProductDto> productDto=productServices.getAllProducts();
        return ResponseEntity.ok(productDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProdById(@PathVariable Long id){
        ProductDto productDto=productServices.getProdById(id);
        return  ResponseEntity.ok(productDto);
    }
}
