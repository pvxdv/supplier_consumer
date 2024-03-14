package com.pvxdv.consumer.dto;


import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;


@Schema(description = "Product Entity")
public record ProductDto (@Schema(description = "product_id", example = "0") Long id,
                          @Schema(description = "product name", example = "carrot") String name,
                          @Schema(description = "product description", example = "The best carrots in our region") String description,
                          @Schema(description = "product price", example = "40.21") BigDecimal price,
                          @Schema(description = "category_id", example = "0") Long categoryId) {
}
