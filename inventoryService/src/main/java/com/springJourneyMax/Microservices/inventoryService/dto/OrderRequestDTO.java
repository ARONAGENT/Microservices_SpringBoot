package com.springJourneyMax.Microservices.inventoryService.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderRequestDTO {
    private Long id; // <-- Add this
    private List<OrderRequestItemDTO> orderItems;
    private BigDecimal totalPrice;
}