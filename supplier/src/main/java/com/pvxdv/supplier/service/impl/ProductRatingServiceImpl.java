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


    @Override
    public CommentDto createNewComment(CommentDto commentDto) {
        return commentToCommentDtoMapper.map(commentRepository.save(commentDtoToCommentMapper.map(commentDto)));
    }

    @Override
    public CommentDto findCommentById(Long id) {
        return commentToCommentDtoMapper.map(commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(COMMENT_NOT_FOUND.formatted(id))));
    }

    @Override
    public void updateCommentById(CommentDto commentDto) {
        if (commentExist(commentDto.id())) {
            Comment commentToUpdate = commentRepository.findById(commentDto.id()).get();
            if (commentDto.rating() != null && commentDto.rating() >= 0 && commentDto.rating() <= 5) {
                commentToUpdate.setRating(commentDto.rating());
            }
            if (commentDto.text() != null) {
                commentToUpdate.setText(commentDto.text());
            }
            log.debug("Saving Comment=%s".formatted(commentToUpdate));
            commentRepository.save(commentToUpdate);
        } else {
            throw new ResourceNotFoundException(COMMENT_NOT_FOUND.formatted(commentDto.id()));
        }
    }

    @Override
    public void deleteCommentId(Long id) {
        if (commentExist(id)) {
            commentRepository.deleteById(id);
            log.debug("Comment with id=%d delete successfully".formatted(id));
        } else {
            throw new ResourceNotFoundException(COMMENT_NOT_FOUND.formatted(id));
        }
    }

    @Override
    public Page<CommentDto> getAllCommentsByProductId(Long productId, Integer offset, Integer limit) {
        return commentRepository.findAllByProductId(productId, PageRequest.of(offset, limit))
                .map(commentToCommentDtoMapper::map);
    }

    @Override
    public ProductWithRatingDto getProductWithAVGRatingAndCommentsByProductId(Long id) {
        if (productExist(id)) {
            Integer avgRating = commentRepository.findProductAVGRatingById(id);
            List<CommentDto> comments = commentRepository.findAllByProductId(id)
                    .stream().map(commentToCommentDtoMapper::map).toList();
            Product product = productRepository.findById(id).get();

            return new ProductWithRatingDto(id, product.getName(), product.getDescription(), product.getPrice(),
                    product.getCategory().getId(), avgRating, comments);
        } else {
            throw new ResourceNotFoundException("Product with id=%d not found");
        }
    }

    private Boolean commentExist(Long id) {
        if (id != null) {
            return commentRepository.findById(id).isPresent();
        } else {
            return false;
        }
    }

    private Boolean productExist(Long id) {
        if (id != null) {
            return productRepository.findById(id).isPresent();
        } else {
            return false;
        }
    }
}
