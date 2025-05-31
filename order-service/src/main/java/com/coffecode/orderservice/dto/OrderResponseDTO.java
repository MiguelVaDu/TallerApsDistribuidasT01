package com.coffecode.orderservice.dto;

import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder // Patrón Builder para construir el DTO de respuesta.
public class OrderResponseDTO {
    private Long id;
    private Long customerId;
    private LocalDateTime orderDate;
    private String status;
    private BigDecimal totalAmount;
    private String paymentTransactionId; // Añadido para mostrar el resultado del pago
    private List<OrderItemResponseDTO> items;
}
