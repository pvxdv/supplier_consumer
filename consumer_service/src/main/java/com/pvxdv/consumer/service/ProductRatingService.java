package com.pvxdv.consumer.service;

import com.pvxdv.consumer.dto.CommentDto;
import com.pvxdv.consumer.dto.PageResponseForCommentDto;
import com.pvxdv.consumer.dto.ProductWithRatingDto;
import org.springframework.http.ResponseEntity;

public interface ProductRatingService {
    ResponseEntity<CommentDto> createNewComment(CommentDto commentDto);

    ResponseEntity<CommentDto> findCommentById(Long commentId);

    void updateCommentById(Long commentId, CommentDto commentDto);

    void deleteCommentId(Long commentId);

    ResponseEntity<PageResponseForCommentDto> getAllCommentsByProductId(Long productId, Integer offset, Integer limit);
    ResponseEntity<ProductWithRatingDto> getProductWithAVGRatingAndCommentsByProductId(Long id);
}
