package com.springJourneyMax.Microservices.orderService.repositories;

import com.springJourneyMax.Microservices.orderService.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdersRepository extends JpaRepository<Orders,Long> {
}
