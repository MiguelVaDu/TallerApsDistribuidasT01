package com.coffecode.productservice.service.impl;

import com.coffecode.productservice.dto.*;
import com.coffecode.productservice.entity.Product;
import com.coffecode.productservice.exception.ProductNotFoundException;
import com.coffecode.productservice.repository.ProductRepository;
import com.coffecode.productservice.service.ProductService;
import lombok.RequiredArgsConstructor; // Lombok para inyección de dependencias por constructor
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductServiceImpl implements ProductService {

    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);
    private final ProductRepository productRepository;

    @Override
    public ProductDTO createProduct(CreateProductRequestDTO createRequest) {
        logger.info("Creando producto con nombre: {}", createRequest.getNombre());
        Product product = new Product();
        product.setNombre(createRequest.getNombre());
        product.setDescripcion(createRequest.getDescripcion());
        product.setPrecio(createRequest.getPrecio());
        product.setStock(createRequest.getStock());

        Product savedProduct = productRepository.save(product);
        logger.info("Producto creado con ID: {}", savedProduct.getId());
        return mapToDTO(savedProduct);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductDTO getProductById(Long productId) {
        logger.info("Buscando producto con ID: {}", productId);
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> {
                    logger.warn("Producto no encontrado con ID: {}", productId);
                    return new ProductNotFoundException("Producto no encontrado con ID: " + productId);
                });
        return mapToDTO(product);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductDTO> getAllProducts() {
        logger.info("Obteniendo todos los productos");
        return productRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ProductDTO updateProduct(Long productId, UpdateProductRequestDTO updateRequest) {
        logger.info("Actualizando producto con ID: {}", productId);
        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> {
                    logger.warn("Producto no encontrado para actualizar, ID: {}", productId);
                    return new ProductNotFoundException("No se puede actualizar. Producto no encontrado con ID: " + productId);
                });

        existingProduct.setNombre(updateRequest.getNombre());
        existingProduct.setDescripcion(updateRequest.getDescripcion());
        existingProduct.setPrecio(updateRequest.getPrecio());
        existingProduct.setStock(updateRequest.getStock());

        Product updatedProduct = productRepository.save(existingProduct);
        logger.info("Producto actualizado con ID: {}", updatedProduct.getId());
        return mapToDTO(updatedProduct);
    }

    @Override
    public ProductDTO updateStock(Long productId, UpdateStockRequestDTO updateStockRequest) {
        logger.info("Actualizando stock para producto ID: {}, nuevo stock: {}", productId, updateStockRequest.getNewStock());
        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> {
                    logger.warn("Producto no encontrado para actualizar stock, ID: {}", productId);
                    return new ProductNotFoundException("No se puede actualizar stock. Producto no encontrado con ID: " + productId);
                });

        existingProduct.setStock(updateStockRequest.getNewStock());
        // Aquí podrías añadir lógica de validación si el stock no puede bajar de 0,
        // o si se integra con un sistema de reservas.
        // Por simplicidad de la tarea, se asigna directamente.

        Product updatedProduct = productRepository.save(existingProduct);
        logger.info("Stock actualizado para producto ID: {}", updatedProduct.getId());
        return mapToDTO(updatedProduct);
    }

    @Override
    public void deleteProduct(Long productId) {
        logger.info("Intentando eliminar producto con ID: {}", productId);
        if (!productRepository.existsById(productId)) {
            logger.warn("Producto no encontrado para eliminar, ID: {}", productId);
            throw new ProductNotFoundException("No se puede eliminar. Producto no encontrado con ID: " + productId);
        }
        productRepository.deleteById(productId);
        logger.info("Producto eliminado con ID: {}", productId);
    }
    @Override
    @Transactional // Es crucial que esta operación sea transaccional
    public void reduceStock(List<UpdateStockItemDTO> items) {
        for (UpdateStockItemDTO item : items) {
            logger.info("Reduciendo stock para producto ID: {} en {} unidades", item.getProductId(), item.getQuantityToReduce());
            Product product = productRepository.findById(item.getProductId())
                    .orElseThrow(() -> new ProductNotFoundException("Producto no encontrado con ID: " + item.getProductId()));

            if (product.getStock() < item.getQuantityToReduce()) {
                throw new IllegalStateException("Stock insuficiente para el producto: " + product.getNombre());
            }

            product.setStock(product.getStock() - item.getQuantityToReduce());
            productRepository.save(product);
        }
    }
    // Métodos de Mapeo
    private ProductDTO mapToDTO(Product product) {
        ProductDTO dto = new ProductDTO();
        dto.setId(product.getId());
        dto.setNombre(product.getNombre());
        dto.setDescripcion(product.getDescripcion());
        dto.setPrecio(product.getPrecio());
        dto.setStock(product.getStock());
        return dto;
    }

}