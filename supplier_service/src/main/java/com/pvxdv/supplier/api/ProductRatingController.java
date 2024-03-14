package com.pvxdv.supplier.api;

import com.pvxdv.supplier.dto.CommentDto;
import com.pvxdv.supplier.dto.PageResponseDto;
import com.pvxdv.supplier.dto.ProductWithRatingDto;
import com.pvxdv.supplier.service.ProductRatingService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/v1/products/rating")
@AllArgsConstructor
public class ProductRatingController {
    private final ProductRatingService productRatingService;

    @PostMapping("/comments")
    public ResponseEntity<CommentDto> addCommentToProduct(@Valid @RequestBody CommentDto commentDto) {
        return new ResponseEntity<>(productRatingService.createNewComment(commentDto), HttpStatus.CREATED);
    }

    @GetMapping("/comments/current/{id}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable("id") @Min(0) Long commentId) {
        return new ResponseEntity<>(productRatingService.findCommentById(commentId), HttpStatus.OK);
    }

    @PutMapping("/comments/current/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateCommentById(@PathVariable("id") @Min(0) Long commentId, @RequestBody CommentDto commentDto) {
        productRatingService.updateCommentById(commentId, commentDto);
    }

    @DeleteMapping("/comments/current/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCommentById(@PathVariable("id") @Min(0) Long commentId) {
        productRatingService.deleteCommentId(commentId);
    }

    @GetMapping("/comments/{id}")
    public ResponseEntity<PageResponseDto<CommentDto>> getCommentsByProductId(@PathVariable("id") @Min(0) Long productId,
                                                                           @RequestParam @Min(0) Integer offset,
                                                                           @RequestParam @Min(1) Integer limit) {
        Page<CommentDto> page = productRatingService.getAllCommentsByProductId(productId, offset, limit);
        return new ResponseEntity<>(PageResponseDto.of(page), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductWithRatingDto> getProductWithRatingAndCommentsById(@PathVariable("id") @Min(0) Long productId) {
        return new ResponseEntity<>(productRatingService.getProductWithAVGRatingAndCommentsByProductId(productId), HttpStatus.OK);
    }
}
