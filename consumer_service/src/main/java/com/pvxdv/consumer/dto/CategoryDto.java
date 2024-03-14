package com.pvxdv.consumer.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Category Entity")
public record CategoryDto (@Schema(description = "category_id", example = "0")  Long id,
                           @Schema(description = "category_name", example = "books") @NotBlank String name){

}
