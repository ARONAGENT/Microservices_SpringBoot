package com.springJourneyMax.Microservices.orderService.controllers;

import com.springJourneyMax.Microservices.orderService.dtos.OrderRequestDTO;
import com.springJourneyMax.Microservices.orderService.services.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/core")
public class OrderController {

    private final OrderService orderService;
    @GetMapping("/HelloOrder")
    public String helloOrder(){
        return "Hello Order Service ....";
    }
    @GetMapping("/all")
    public ResponseEntity<List<OrderRequestDTO>> allOrders(){
        List<OrderRequestDTO> orderRequestDTOS=orderService.getAllOrders();
        return ResponseEntity.ok(orderRequestDTOS);
    }

    @PostMapping("/add")
    public ResponseEntity<OrderRequestDTO> createOrder(@RequestBody OrderRequestDTO orderRequestDTO){
        OrderRequestDTO orderRequestDTO1=orderService.createOrder(orderRequestDTO);
        return ResponseEntity.ok(orderRequestDTO1);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderRequestDTO> getOrdersById(@PathVariable Long id){
        OrderRequestDTO orderRequestDTO=orderService.getOrdersById(id);
        return ResponseEntity.ok(orderRequestDTO);
    }
}
