package com.coffecode.orderservice.dto.external;

import lombok.Data;
// Este DTO es una copia del ProductDTO de product-service.
@Data
public class ProductDTO {
    private Long id;
    private String nombre;
    private String descripcion;
    private Double precio;
    private Integer stock;
}