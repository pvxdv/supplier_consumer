package com.pvxdv.supplier.service;

import com.pvxdv.supplier.dto.CommentDto;
import com.pvxdv.supplier.dto.ProductWithRatingDto;
import org.springframework.data.domain.Page;

public interface ProductRatingService {
    CommentDto createNewComment(CommentDto commentDto);

    CommentDto findCommentById(Long commentId);

    void updateCommentById(Long commentId, CommentDto commentDto);

    void deleteCommentId(Long commentId);

    Page<CommentDto> getAllCommentsByProductId(Long productId, Integer offset, Integer limit);
    ProductWithRatingDto getProductWithAVGRatingAndCommentsByProductId(Long productId);
}
