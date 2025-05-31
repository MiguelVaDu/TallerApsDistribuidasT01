package com.coffecode.paymentservice.strategy;

import com.coffecode.paymentservice.dto.PaymentResponseDTO;

// La interfaz comun para todas las estrategias de pago
public interface PaymentStrategy {
    PaymentResponseDTO processPayment();
    String getStrategyName();
}