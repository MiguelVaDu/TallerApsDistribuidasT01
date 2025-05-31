package com.coffecode.orderservice.dto;

import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;

@Data
@Builder
public class OrderItemResponseDTO {
    private Long productId;
    private String productName; // Mejorado para mostrar el nombre del producto
    private Integer quantity;
    private BigDecimal priceAtOrder;
}