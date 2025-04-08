package com.springJourneyMax.Microservices.orderService.services;

import com.springJourneyMax.Microservices.orderService.clients.InventoryFeignClient;
import com.springJourneyMax.Microservices.orderService.dtos.OrderRequestDTO;
import com.springJourneyMax.Microservices.orderService.entity.OrderItem;
import com.springJourneyMax.Microservices.orderService.entity.Orders;
import com.springJourneyMax.Microservices.orderService.entity.enums.OrderStatus;
import com.springJourneyMax.Microservices.orderService.repositories.OrdersRepository;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderService {

    private final OrdersRepository ordersRepository ;
    private final ModelMapper modelMapper;
    private final InventoryFeignClient inventoryFeignClient;

    public List<OrderRequestDTO> getAllOrders(){
        log.info("Fetching All Orders ....");
        List<Orders> ordersList=ordersRepository.findAll();
        return ordersList.stream().map(orders -> modelMapper.map(orders,OrderRequestDTO.class)).toList();
    }

    public OrderRequestDTO getOrdersById(Long id){
        log.info("Fetching Orders By OrderId {} ....",id);
        Orders orders=ordersRepository.findById(id).orElseThrow(()-> new RuntimeException("Order noty found"));
        return modelMapper.map(orders,OrderRequestDTO.class);
    }
    @Retry(name = "inventoryRetry" ,fallbackMethod="createOrderFallback")
    public OrderRequestDTO createOrder(OrderRequestDTO orderRequestDTO) {
        log.info("Starting createOrder for request: {}", orderRequestDTO);
        Double totalPrice=inventoryFeignClient.reduceStock(orderRequestDTO);
        Orders orders = modelMapper.map(orderRequestDTO, Orders.class);
            for (OrderItem item : orders.getOrderItems()) {
                item.setOrders(orders);
            }
            orders.setTotalPrice(totalPrice);
            orders.setOrderStatus(OrderStatus.CONFIRMED);
        ordersRepository.save(orders);
        return modelMapper.map(orders, OrderRequestDTO.class);
    }
    public OrderRequestDTO createOrderFallback(OrderRequestDTO orderRequestDTO,Throwable throwable){
        log.error("Fallback occurred due to {}",throwable.getMessage());
        return new OrderRequestDTO();
    }
}
