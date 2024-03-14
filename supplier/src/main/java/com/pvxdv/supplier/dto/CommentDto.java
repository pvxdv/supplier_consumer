package com.pvxdv.supplier.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public record CommentDto (@Min(0) Long id, @Min(0) @Max(5) Integer rating, String text, @Min(0) Long productId){
}
