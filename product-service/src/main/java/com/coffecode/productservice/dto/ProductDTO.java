package com.coffecode.productservice.dto;

import lombok.Data;
// Puedes añadir validaciones de Jakarta Bean Validation aquí si es necesario
import jakarta.validation.constraints.*;

@Data
public class ProductDTO {
    private Long id;
    @NotBlank // Ejemplo de validación
    private String nombre;
    private String descripcion;
    @Positive // Ejemplo de validación
    private Double precio;
    @Min(0) // Ejemplo de validación
    private Integer stock;
}