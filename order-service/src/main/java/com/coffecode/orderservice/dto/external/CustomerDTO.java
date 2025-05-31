package com.coffecode.orderservice.dto.external;

import lombok.Data;
// Este DTO es una copia del CustomerDTO de customer-service
// para que OpenFeign sepa cómo deserializar la respuesta.
@Data
public class CustomerDTO {
    private Long id;
    private String nombre;
    private String email;
    private String direccion;
}
