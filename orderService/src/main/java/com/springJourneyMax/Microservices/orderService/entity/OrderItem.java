package com.springJourneyMax.Microservices.orderService.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "orderItem")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long prodId;

    private Integer quantity;

    @ManyToOne()
    @JoinColumn(name = "order_id")
    private  Orders orders;

}
