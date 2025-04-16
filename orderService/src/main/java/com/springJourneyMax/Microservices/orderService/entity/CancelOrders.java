package com.springJourneyMax.Microservices.orderService.entity;

import com.springJourneyMax.Microservices.orderService.entity.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "cancel_orders")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CancelOrders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    private LocalDateTime localDateTime;

}
