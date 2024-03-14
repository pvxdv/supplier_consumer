package com.pvxdv.consumer.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.util.List;

@Schema(description = "All available information about a specific product")
public record ProductWithRatingDto(Long id, String name, String description, BigDecimal price,
                                   Long categoryId, Integer avgRating, List<CommentDto> comments) {
}
