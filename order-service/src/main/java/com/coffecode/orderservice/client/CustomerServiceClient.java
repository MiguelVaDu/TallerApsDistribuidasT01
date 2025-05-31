package com.coffecode.orderservice.client;

import com.coffecode.orderservice.dto.external.CustomerDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// Patrón Adapter: Adapta la API REST de customer-service a una interfaz Java.
@FeignClient(name = "customer-service")
public interface CustomerServiceClient {
    @GetMapping("/api/customers/{id}")
    CustomerDTO getCustomerById(@PathVariable("id") Long id);
}