package com.springJourneyMax.Microservices.orderService.controllers;

import com.springJourneyMax.Microservices.orderService.dtos.OrderRequestDTO;
import com.springJourneyMax.Microservices.orderService.services.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    @GetMapping("/all")
    public ResponseEntity<List<OrderRequestDTO>> allOrders(){
        List<OrderRequestDTO> orderRequestDTOS=orderService.getAllOrders();
        return ResponseEntity.ok(orderRequestDTOS);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderRequestDTO> getOrdersById(@PathVariable Long id){
        OrderRequestDTO orderRequestDTO=orderService.getOrdersById(id);
        return ResponseEntity.ok(orderRequestDTO);
    }
}
