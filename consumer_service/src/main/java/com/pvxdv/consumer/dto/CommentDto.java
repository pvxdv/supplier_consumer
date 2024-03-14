package com.pvxdv.consumer.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

@Schema(description = "Comment Entity")
public record CommentDto(@Schema(description = "comment_id", example = "0") Long id,
                         @Schema(description = "product rating", example = "0") @Min(0) @Max(5) Integer rating,
                         @Schema(description = "feedback (optional)", example = "good price") String text,
                         @Schema(description = "product_id", example = "0") @Min(0) Long productsId){
}
