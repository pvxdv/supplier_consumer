package com.pvxdv.supplier.dto;

import java.math.BigDecimal;
import java.util.List;

public record ProductWithRatingDto(Long id, String name, String description, BigDecimal price,
                                   Long categoryId, Integer avgRating, List<CommentDto> comments) {
}
