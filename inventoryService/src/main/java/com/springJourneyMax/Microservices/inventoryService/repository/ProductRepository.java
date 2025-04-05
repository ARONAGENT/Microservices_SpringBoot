package com.springJourneyMax.Microservices.inventoryService.repository;

import com.springJourneyMax.Microservices.inventoryService.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Long> {

}
