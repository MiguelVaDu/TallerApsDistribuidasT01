package com.coffecode.paymentservice.strategy;

import com.coffecode.paymentservice.dto.PaymentResponseDTO;
import org.springframework.stereotype.Component;
import java.util.UUID;


@Component("CREDIT_CARD")
public class CreditCardPaymentStrategy implements PaymentStrategy {
    @Override
    public PaymentResponseDTO processPayment() {
        System.out.println("Procesando pago simulado con Tarjeta de Crédito...");
        return new PaymentResponseDTO(UUID.randomUUID().toString(), "APPROVED");
    }

    @Override
    public String getStrategyName() {
        return "CREDIT_CARD";
    }
}

