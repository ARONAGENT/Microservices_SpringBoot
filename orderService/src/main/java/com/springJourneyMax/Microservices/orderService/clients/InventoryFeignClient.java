package com.springJourneyMax.Microservices.orderService.clients;

import com.springJourneyMax.Microservices.orderService.dtos.OrderRequestDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "inventoryService",path = "http://localhost:9010/inventory")
public interface InventoryFeignClient {
    @PutMapping("/products/updateStock")
    Double reduceStock(@RequestBody OrderRequestDTO orderRequestDTO);
}
