package com.pvxdv.supplier.mapper.impl;

import com.pvxdv.supplier.dto.CommentDto;
import com.pvxdv.supplier.mapper.Mapper;
import com.pvxdv.supplier.model.Comment;
import org.springframework.stereotype.Component;

@Component
public class CommentToCommentDtoMapper implements Mapper<Comment, CommentDto> {
    @Override
    public CommentDto map(Comment comment) {
        return new CommentDto(comment.getId(), comment.getRating(), comment.getText(), comment.getProduct().getId());
    }
}

