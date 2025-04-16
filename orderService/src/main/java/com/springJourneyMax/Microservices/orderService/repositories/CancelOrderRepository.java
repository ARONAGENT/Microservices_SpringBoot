package com.springJourneyMax.Microservices.orderService.repositories;

import com.springJourneyMax.Microservices.orderService.entity.CancelOrders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CancelOrderRepository extends JpaRepository<CancelOrders,Long> {
}
