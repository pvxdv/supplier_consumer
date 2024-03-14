package com.pvxdv.supplier.mapper.impl;

import com.pvxdv.supplier.dto.CommentDto;
import com.pvxdv.supplier.exception.ResourceNotFoundException;
import com.pvxdv.supplier.mapper.Mapper;
import com.pvxdv.supplier.model.Comment;
import com.pvxdv.supplier.model.Product;
import com.pvxdv.supplier.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CommentDtoToCommentMapper implements Mapper<CommentDto, Comment> {
    private final ProductRepository productRepository;

    @Override
    public Comment map(CommentDto commentDto) {
        Comment comment = new Comment();
        comment.setId(commentDto.id());
        comment.setRating(commentDto.rating());
        comment.setText(commentDto.text());
        comment.setProduct(getProduct(commentDto.productsId()));
        return comment;
    }

    private Product getProduct(Long id) {
        return Optional.ofNullable(id)
                .flatMap(productRepository::findById)
                .orElseThrow(() -> new ResourceNotFoundException("Product with id=%d not found".formatted(id)));
    }
}
