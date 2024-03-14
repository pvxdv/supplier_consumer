package com.pvxdv.supplier.util.searchFilter;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Value;

import java.math.BigDecimal;

@Value
public class ProductFilter {
    String name;
    String description;
    BigDecimal price;
    Long categoryId;
    @PositiveOrZero
    int offset;
    @Positive
    int limit;
}
