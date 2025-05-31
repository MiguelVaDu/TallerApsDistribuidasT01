package com.coffecode.orderservice.client;

import com.coffecode.orderservice.dto.external.ProductDTO;
import com.coffecode.orderservice.dto.external.UpdateStockItemDTO;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

// Patrón Adapter: Adapta la API REST de product-service a una interfaz Java.
@FeignClient(name = "product-service")
public interface ProductServiceClient {

    @GetMapping("/api/products/{id}")
    @CircuitBreaker(name = "product-service", fallbackMethod = "fallbackGetProductById")
    @Retry(name = "product-service")
    ProductDTO getProductById(@PathVariable("id") Long id);

    @PostMapping("/api/products/stock/reduce")
    void reduceStock(@RequestBody List<UpdateStockItemDTO> items);

    // Método de fallback para el Circuit Breaker
    default ProductDTO fallbackGetProductById(Long id, Throwable t) {
        // Loggear el error 't'
        System.out.println("Fallback ejecutado para getProductById. Error: " + t.getMessage());
        // Devuelve un objeto por defecto o nulo para indicar que el servicio no está disponible
        ProductDTO defaultProduct = new ProductDTO();
        defaultProduct.setId(id);
        defaultProduct.setNombre("Servicio de Productos no disponible");
        return defaultProduct;
    }
}
