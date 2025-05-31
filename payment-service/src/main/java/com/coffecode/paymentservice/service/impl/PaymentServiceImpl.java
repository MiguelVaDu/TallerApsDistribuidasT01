package com.coffecode.paymentservice.service.impl;

import com.coffecode.paymentservice.dto.PaymentRequestDTO;
import com.coffecode.paymentservice.dto.PaymentResponseDTO;
import com.coffecode.paymentservice.service.PaymentService;
import com.coffecode.paymentservice.strategy.PaymentStrategy;
import com.coffecode.paymentservice.strategy.PaymentStrategyFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentServiceImpl implements PaymentService {

    private final PaymentStrategyFactory strategyFactory;

    @Override
    public PaymentResponseDTO processPayment(PaymentRequestDTO request) {
        log.info("Procesando pago para la orden {} usando el método {}",
                request.getOrderId(), request.getPaymentMethod());

        // Patrón Strategy: Se obtiene la estrategia concreta desde la factory
        PaymentStrategy strategy = strategyFactory.getStrategy(request.getPaymentMethod());

        // Se ejecuta el pago
        PaymentResponseDTO response = strategy.processPayment();
        log.info("Pago para la orden {} finalizado con estado {} y transacción {}",
                request.getOrderId(), response.getStatus(), response.getTransactionId());

        return response;
    }
}