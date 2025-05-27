package com.coffecode.productservice.service;

import com.coffecode.productservice.dto.CreateProductRequestDTO;
import com.coffecode.productservice.dto.ProductDTO;
import com.coffecode.productservice.dto.UpdateProductRequestDTO;
import com.coffecode.productservice.dto.UpdateStockRequestDTO;

import java.util.List;

public interface ProductService {
    ProductDTO createProduct(CreateProductRequestDTO createRequest);
    ProductDTO getProductById(Long productId);
    List<ProductDTO> getAllProducts();
    ProductDTO updateProduct(Long productId, UpdateProductRequestDTO updateRequest);
    ProductDTO updateStock(Long productId, UpdateStockRequestDTO updateStockRequest);
    void deleteProduct(Long productId);
}