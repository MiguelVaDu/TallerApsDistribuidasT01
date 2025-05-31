package com.coffecode.productservice.controller;

import com.coffecode.productservice.dto.*;
import com.coffecode.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid; // Importante para que se activen las validaciones en los DTOs

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(@Valid @RequestBody CreateProductRequestDTO createRequest) {
        ProductDTO createdProduct = productService.createProduct(createRequest);
        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id) {
        ProductDTO product = productService.getProductById(id);
        return ResponseEntity.ok(product);
    }

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        List<ProductDTO> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id,
                                                    @Valid @RequestBody UpdateProductRequestDTO updateRequest) {
        ProductDTO updatedProduct = productService.updateProduct(id, updateRequest);
        return ResponseEntity.ok(updatedProduct);
    }

    @PatchMapping("/{id}/stock") // Usamos PATCH para actualizaciones parciales como el stock
    public ResponseEntity<ProductDTO> updateStock(@PathVariable Long id,
                                                  @Valid @RequestBody UpdateStockRequestDTO updateStockRequest) {
        ProductDTO updatedProduct = productService.updateStock(id, updateStockRequest);
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build(); // 204 No Content es una respuesta común para DELETE exitoso
    }

    @PostMapping("/stock/reduce")
    public ResponseEntity<Void> reduceStock(@RequestBody List<UpdateStockItemDTO> items) {
        productService.reduceStock(items);
        return ResponseEntity.ok().build();
    }
}