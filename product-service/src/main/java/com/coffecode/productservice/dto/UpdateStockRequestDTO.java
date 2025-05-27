package com.coffecode.productservice.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class UpdateStockRequestDTO {
    @NotNull(message = "El nuevo stock no puede ser nulo")
    @Min(value = 0, message = "El stock no puede ser negativo")
    private Integer newStock;
}
