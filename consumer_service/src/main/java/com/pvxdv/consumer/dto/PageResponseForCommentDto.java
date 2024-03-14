package com.pvxdv.consumer.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Value;

import java.util.List;

@Schema(description = "List of comments entity by pages")
@Value
public class PageResponseForCommentDto {
    List<CommentDto> content;
    Metadata metadata;

    @Value
    public static class Metadata {
        int page;
        int size;
        long totalElements;
    }
}
