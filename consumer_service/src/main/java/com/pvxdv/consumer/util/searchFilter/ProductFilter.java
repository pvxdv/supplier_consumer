package com.pvxdv.consumer.util.searchFilter;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;


public record ProductFilter(String name, String description, BigDecimal price, Long categoryId,
                            @PositiveOrZero Integer offset, @PositiveOrZero @NotNull Integer limit) {
}
