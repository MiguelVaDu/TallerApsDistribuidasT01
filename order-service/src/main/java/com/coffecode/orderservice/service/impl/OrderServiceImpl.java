package com.coffecode.orderservice.service.impl;

import com.coffecode.orderservice.client.CustomerServiceClient;
import com.coffecode.orderservice.client.PaymentServiceClient;
import com.coffecode.orderservice.client.ProductServiceClient;
import com.coffecode.orderservice.dto.*;
import com.coffecode.orderservice.dto.external.PaymentRequestDTO;
import com.coffecode.orderservice.dto.external.PaymentResponseDTO;
import com.coffecode.orderservice.dto.external.ProductDTO;
import com.coffecode.orderservice.dto.external.UpdateStockItemDTO;
import com.coffecode.orderservice.entity.Order;
import com.coffecode.orderservice.entity.OrderItem;
import com.coffecode.orderservice.repository.OrderRepository;
import com.coffecode.orderservice.service.OrderService;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ProductServiceClient productServiceClient;
    private final CustomerServiceClient customerServiceClient;
    private final PaymentServiceClient paymentServiceClient;

    @Override
    @Transactional
    public OrderResponseDTO placeOrder(CreateOrderRequestDTO request) {
        log.info("Iniciando creación de orden para el cliente ID: {}", request.getCustomerId());

        // 1. Validar cliente
        validateCustomer(request.getCustomerId());

        Order order = new Order();
        order.setCustomerId(request.getCustomerId());
        order.setOrderDate(LocalDateTime.now());
        order.setStatus("PENDING_VALIDATION");

        List<OrderItem> orderItems = new ArrayList<>();
        BigDecimal totalAmount = BigDecimal.ZERO;

        // 2. Validar productos, stock y calcular totales
        for (OrderItemRequestDTO itemRequest : request.getItems()) {
            ProductDTO product = productServiceClient.getProductById(itemRequest.getProductId());
            if (product == null || product.getNombre().contains("no disponible")) {
                throw new RuntimeException("El producto con ID " + itemRequest.getProductId() + " no está disponible.");
            }
            if (product.getStock() < itemRequest.getQuantity()) {
                throw new RuntimeException("Stock insuficiente para el producto: " + product.getNombre());
            }

            OrderItem orderItem = createOrderItem(itemRequest, product, order);
            orderItems.add(orderItem);
            totalAmount = totalAmount.add(orderItem.getPriceAtOrder().multiply(new BigDecimal(orderItem.getQuantity())));
        }

        order.setItems(orderItems);
        order.setTotalAmount(totalAmount);
        order.setStatus("PENDING_PAYMENT");
        Order savedOrder = orderRepository.save(order);
        log.info("Orden {} guardada con estado PENDING_PAYMENT.", savedOrder.getId());

        // 3. Procesar el pago
        PaymentResponseDTO paymentResponse = processPaymentForOrder(savedOrder);

        // 4. Actualizar estado final de la orden
        if ("APPROVED".equals(paymentResponse.getStatus())) {
            savedOrder.setStatus("PROCESSING");
            savedOrder.setPaymentTransactionId(paymentResponse.getTransactionId());

            // --- ACTUALIZACIÓN DE STOCK ---
            try {
                List<UpdateStockItemDTO> stockUpdateItems = request.getItems().stream()
                        .map(item -> new UpdateStockItemDTO(item.getProductId(), item.getQuantity()))
                        .collect(Collectors.toList());

                productServiceClient.reduceStock(stockUpdateItems);
                log.info("Stock actualizado exitosamente para la orden {}.", savedOrder.getId());
            } catch (Exception e) {
                log.error("Error al actualizar el stock para la orden {}. El pago fue procesado. " +
                        "Se requiere intervención manual para reconciliar el stock.", savedOrder.getId(), e);
                savedOrder.setStatus("PROCESSING_STOCK_ERROR"); // Un estado especial para indicar el problema
            }


        } else {
            savedOrder.setStatus("PAYMENT_FAILED");
        }

        Order finalOrder = orderRepository.save(savedOrder);
        log.info("Orden {} finalizada con estado {}.", finalOrder.getId(), finalOrder.getStatus());

        return mapToResponseDTO(finalOrder);
    }

    private void validateCustomer(Long customerId) {
        try {
            customerServiceClient.getCustomerById(customerId);
            log.info("Cliente con ID {} validado exitosamente.", customerId);
        } catch (FeignException.NotFound e) {
            log.error("Cliente no encontrado con ID: {}", customerId);
            throw new RuntimeException("Cliente no válido.");
        }
    }

    private OrderItem createOrderItem(OrderItemRequestDTO itemRequest, ProductDTO product, Order order) {
        OrderItem orderItem = new OrderItem();
        orderItem.setProductId(product.getId());
        orderItem.setProductName(product.getNombre());
        orderItem.setQuantity(itemRequest.getQuantity());
        orderItem.setPriceAtOrder(BigDecimal.valueOf(product.getPrecio()));
        orderItem.setOrder(order);
        return orderItem;
    }

    private PaymentResponseDTO processPaymentForOrder(Order order) {
        PaymentRequestDTO paymentRequest = PaymentRequestDTO.builder()
                .orderId(order.getId())
                .amount(order.getTotalAmount())
                .paymentMethod("CREDIT_CARD") // Metodo de pago por defecto para la simulación
                .build();
        log.info("Iniciando pago para la orden {}", order.getId());
        return paymentServiceClient.processPayment(paymentRequest);
    }


    @Override
    public OrderResponseDTO getOrderById(Long orderId) {
        log.info("Buscando orden con ID: {}", orderId);
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Pedido no encontrado"));
        return mapToResponseDTO(order);
    }

    private OrderResponseDTO mapToResponseDTO(Order order) {
        return OrderResponseDTO.builder()
                .id(order.getId())
                .customerId(order.getCustomerId())
                .orderDate(order.getOrderDate())
                .status(order.getStatus())
                .totalAmount(order.getTotalAmount())
                .paymentTransactionId(order.getPaymentTransactionId())
                .items(order.getItems().stream().map(this::mapToItemResponseDTO).collect(Collectors.toList()))
                .build();
    }

    private OrderItemResponseDTO mapToItemResponseDTO(OrderItem item) {
        // Usando el Patrón Builder
        return OrderItemResponseDTO.builder()
                .productId(item.getProductId())
                .productName(item.getProductName())
                .quantity(item.getQuantity())
                .priceAtOrder(item.getPriceAtOrder())
                .build();
    }

}
