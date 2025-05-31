package com.coffecode.orderservice.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.util.List;

@Data
public class CreateOrderRequestDTO {

    @NotNull(message = "El ID del cliente no puede ser nulo.")
    private Long customerId;

    @NotEmpty(message = "La lista de ítems no puede estar vacía.")
    @Valid // Esta anotación es CRUCIAL. Le dice a Spring que valide cada objeto dentro de la lista.
    private List<OrderItemRequestDTO> items;
}
