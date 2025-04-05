package com.springJourneyMax.Microservices.orderService.dtos;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderRequestDTO {
    private Long id;
    private List<OrderRequestItemDTO> items;
    private BigDecimal price;
}
