package com.pvxdv.consumer.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;


@Schema(description = "List of Categories")
public record CategoryListDto(List<CategoryDto> categories) {
}
