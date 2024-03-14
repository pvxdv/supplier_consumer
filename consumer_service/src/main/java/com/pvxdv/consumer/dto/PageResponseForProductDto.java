package com.pvxdv.consumer.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Value;
import java.util.List;

@Schema(description = "List of products entity by pages")
@Value
public class PageResponseForProductDto{
    List<ProductDto> content;
    Metadata metadata;

    @Value
    public static class Metadata {
        int page;
        int size;
        long totalElements;
    }
}
