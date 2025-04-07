package com.springJourneyMax.Microservices.inventoryService.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "orderService",path = "/orders")
public interface OrderFeignClient {

    @GetMapping("/core/HelloOrder")
    String helloWorld();
}
