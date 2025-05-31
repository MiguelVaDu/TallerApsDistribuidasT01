package com.coffecode.paymentservice.service;

import com.coffecode.paymentservice.dto.PaymentRequestDTO;
import com.coffecode.paymentservice.dto.PaymentResponseDTO;

public interface PaymentService {
    PaymentResponseDTO processPayment(PaymentRequestDTO request);
}
