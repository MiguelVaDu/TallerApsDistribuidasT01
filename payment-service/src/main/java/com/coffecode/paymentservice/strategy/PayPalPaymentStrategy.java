package com.coffecode.paymentservice.strategy;

import com.coffecode.paymentservice.dto.PaymentResponseDTO;
import org.springframework.stereotype.Component;
import java.util.UUID;

@Component("PAYPAL")
public class PayPalPaymentStrategy implements PaymentStrategy {
    @Override
    public PaymentResponseDTO processPayment() {
        System.out.println("Procesando pago simulado con PayPal...");
        return new PaymentResponseDTO(UUID.randomUUID().toString(), "APPROVED");
    }

    @Override
    public String getStrategyName() {
        return "PAYPAL";
    }
}
