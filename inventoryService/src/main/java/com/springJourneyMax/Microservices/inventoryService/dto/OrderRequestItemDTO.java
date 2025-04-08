package com.springJourneyMax.Microservices.inventoryService.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderRequestItemDTO {
    private Long id;
    private Long prodId;
    private Integer quantity;
}
