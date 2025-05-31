package com.coffecode.orderservice.dto.external;

import lombok.Data;

@Data
public class PaymentResponseDTO {
    private String transactionId;
    private String status;
}