package com.springJourneyMax.Microservices.orderService.services;

import com.springJourneyMax.Microservices.orderService.dtos.OrderRequestDTO;
import com.springJourneyMax.Microservices.orderService.entity.Orders;
import com.springJourneyMax.Microservices.orderService.repositories.OrdersRepository;
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
}
