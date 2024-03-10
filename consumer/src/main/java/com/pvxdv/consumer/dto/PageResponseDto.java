package com.pvxdv.consumer.dto;

import lombok.Value;
import java.util.List;

@Value
public class PageResponseDto<T>{
    List<T> content;
    Metadata metadata;

    @Value
    public static class Metadata {
        int page;
        int size;
        long totalElements;
    }
}
