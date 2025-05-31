package com.coffecode.customerservice.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder // Patrón Builder para construir el DTO de forma limpia
public class CustomerDTO {
    private Long id;
    private String nombre;
    private String email;
    private String direccion;
}
