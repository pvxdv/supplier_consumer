package com.pvxdv.supplier.dto;


import jakarta.validation.constraints.*;

import java.math.BigDecimal;


public record ProductDto(@PositiveOrZero Long id,
                         @NotBlank @Size(min = 4, max = 30) String name,
                         @NotBlank String description,
                         @NotNull
                         @DecimalMin(value = "0.0", inclusive = false) BigDecimal price,
                         @NotNull @PositiveOrZero Long categoryId) {

}
