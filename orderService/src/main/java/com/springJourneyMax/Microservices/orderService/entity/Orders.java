package com.springJourneyMax.Microservices.orderService.entity;

import com.springJourneyMax.Microservices.orderService.entity.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    private Double totalPrice;

    @OneToMany(mappedBy = "orders", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<OrderItem> items;
}
