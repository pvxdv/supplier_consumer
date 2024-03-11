package com.pvxdv.consumer.dto;

import lombok.Value;
import java.util.List;

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
