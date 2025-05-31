package com.coffecode.orderservice.client;

import com.coffecode.orderservice.dto.external.PaymentRequestDTO;
import com.coffecode.orderservice.dto.external.PaymentResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

// Patrón Adapter: Adapta la API REST de payment-service a una interfaz Java.
@FeignClient(name = "payment-service")
public interface PaymentServiceClient {
    @PostMapping("/api/payments/process")
    PaymentResponseDTO processPayment(@RequestBody PaymentRequestDTO paymentRequest);
}