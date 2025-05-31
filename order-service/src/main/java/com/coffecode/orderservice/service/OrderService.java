package com.coffecode.orderservice.service;

import com.coffecode.orderservice.dto.CreateOrderRequestDTO;
import com.coffecode.orderservice.dto.OrderResponseDTO;

public interface OrderService {
    OrderResponseDTO placeOrder(CreateOrderRequestDTO request);
    OrderResponseDTO getOrderById(Long orderId);
}
