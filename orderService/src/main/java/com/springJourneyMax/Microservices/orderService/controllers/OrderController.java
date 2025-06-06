package com.springJourneyMax.Microservices.orderService.controllers;

import com.springJourneyMax.Microservices.orderService.config.User_tracingConfig;
import com.springJourneyMax.Microservices.orderService.dtos.OrderRequestDTO;
import com.springJourneyMax.Microservices.orderService.services.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/core")
@RefreshScope
public class OrderController {

    private final OrderService orderService;
    private final User_tracingConfig userTracingConfig;

    @GetMapping("/HelloOrder")
    public String helloOrder(@RequestHeader("X_User-Id") Long userid){
        return "Hello Order Service ....Your userid "+userid;
    }

    @Value("${rohan.properties}")
    private String myEnvironmentVariable;

    @GetMapping("/helloUserTracings")
    public String helloSpringProfile(){
        if(userTracingConfig.isUserTracingEnabled()){
            return "User tracing enabled ";
        }else {
            return "User tracing disabled";
        }
    }

    @GetMapping("/helloEnvProfiles")
    public String hello_userTracing(){
        return "Hello Order Service userTracing is enabled : "+myEnvironmentVariable;
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

    @DeleteMapping("/order-cancelled/{id}")
    public ResponseEntity<OrderRequestDTO> deleteOrder(@PathVariable Long id){
        OrderRequestDTO orderRequestDTO1=orderService.cancelOrder(id);
        return ResponseEntity.ok(orderRequestDTO1);
    }
}
