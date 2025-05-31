package com.coffecode.productservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateStockItemDTO {
    private Long productId;
    private Integer quantityToReduce; // Cantidad a reducir del stock
}