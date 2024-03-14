package com.pvxdv.supplier.service.impl;

import com.pvxdv.supplier.dto.CommentDto;
import com.pvxdv.supplier.dto.ProductWithRatingDto;
import com.pvxdv.supplier.exception.ResourceNotFoundException;
import com.pvxdv.supplier.mapper.impl.CommentDtoToCommentMapper;
import com.pvxdv.supplier.mapper.impl.CommentToCommentDtoMapper;
import com.pvxdv.supplier.model.Comment;
import com.pvxdv.supplier.model.Product;
import com.pvxdv.supplier.repository.CommentRepository;
import com.pvxdv.supplier.repository.ProductRepository;
import com.pvxdv.supplier.service.ProductRatingService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class ProductRatingServiceImpl implements ProductRatingService {
    private final CommentRepository commentRepository;
    private final ProductRepository productRepository;
    private final CommentDtoToCommentMapper commentDtoToCommentMapper;
    private final CommentToCommentDtoMapper commentToCommentDtoMapper;
    private final String COMMENT_NOT_FOUND = "Comment with id=%d not found";
    private final String SAVE_COMMENT = "Saving Comment=%s";


    @Override
    public CommentDto createNewComment(CommentDto commentDto) {
        Comment commentToSave = commentDtoToCommentMapper.map(commentDto);
        log.debug(SAVE_COMMENT.formatted(commentToSave));
        return commentToCommentDtoMapper.map(commentRepository.save(commentToSave));
    }

    @Override
    public CommentDto findCommentById(Long commentId) {
        return commentToCommentDtoMapper.map(commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException(COMMENT_NOT_FOUND.formatted(commentId))));
    }

    @Override
    public void updateCommentById(Long commentId, CommentDto commentDto) {
        if (commentExist(commentId)) {
            Comment commentToUpdate = commentRepository.findById(commentId).get();
            if (commentDto.rating() != null && commentDto.rating() >= 0 && commentDto.rating() <= 5) {
                commentToUpdate.setRating(commentDto.rating());
            }
            if (commentDto.text() != null) {
                commentToUpdate.setText(commentDto.text());
            }
            log.debug(SAVE_COMMENT.formatted(commentToUpdate));
            commentRepository.save(commentToUpdate);
        } else {
            throw new ResourceNotFoundException(COMMENT_NOT_FOUND.formatted(commentDto.id()));
        }
    }

    @Override
    public void deleteCommentId(Long commentId) {
        if (commentExist(commentId)) {
            commentRepository.deleteById(commentId);
            log.debug("Comment with id=%d delete successfully".formatted(commentId));
        } else {
            throw new ResourceNotFoundException(COMMENT_NOT_FOUND.formatted(commentId));
        }
    }

    @Override
    public Page<CommentDto> getAllCommentsByProductId(Long productId, Integer offset, Integer limit) {
        return commentRepository.findAllByProductId(productId, PageRequest.of(offset, limit))
                .map(commentToCommentDtoMapper::map);
    }

    @Override
    public ProductWithRatingDto getProductWithAVGRatingAndCommentsByProductId(Long productId) {
        if (productExist(productId)) {
            Integer avgRating = commentRepository.findProductAVGRatingById(productId);
            List<CommentDto> comments = commentRepository.findAllByProductId(productId)
                    .stream().map(commentToCommentDtoMapper::map).toList();
            Product product = productRepository.findById(productId).get();

            return new ProductWithRatingDto(productId, product.getName(), product.getDescription(), product.getPrice(),
                    product.getCategory().getId(), avgRating, comments);
        } else {
            throw new ResourceNotFoundException("Product with id=%d not found");
        }
    }

    private Boolean commentExist(Long commentId) {
        if (commentId != null) {
            return commentRepository.findById(commentId).isPresent();
        } else {
            return false;
        }
    }

    private Boolean productExist(Long productId) {
        if (productId != null) {
            return productRepository.findById(productId).isPresent();
        } else {
            return false;
        }
    }
}
