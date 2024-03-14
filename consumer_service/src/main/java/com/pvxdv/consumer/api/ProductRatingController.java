package com.pvxdv.consumer.api;


import com.pvxdv.consumer.dto.CommentDto;
import com.pvxdv.consumer.dto.PageResponseForCommentDto;
import com.pvxdv.consumer.dto.ProductWithRatingDto;
import com.pvxdv.consumer.service.ProductRatingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Tag(name = "ProductRatingController", description = "CRUD operations with products comments & get products rating")
@RestController
@RequestMapping("api/v1/products/rating")
@AllArgsConstructor
public class ProductRatingController {
    private final ProductRatingService productRatingService;
    @Operation(
            summary = "Add new comment",
            description = "Add new comment with feedback & rating to current product, by product_id"
    )
    @PostMapping("/comments")
    public ResponseEntity<CommentDto> addCommentToProduct(@Valid @RequestBody CommentDto commentDto) {
        return productRatingService.createNewComment(commentDto);
    }

    @Operation(
            summary = "Get current comment",
            description = "Get current comment by comment_id"
    )
    @GetMapping("/comments/current/{id}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable("id") @Min(0) Long commentId) {
        return productRatingService.findCommentById(commentId);
    }

    @Operation(
            summary = "Update current comment",
            description = "Update current comment by comment_id"
    )
    @PutMapping("/comments/current/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateCommentById(@PathVariable("id") @Min(0) Long commentId, @RequestBody CommentDto commentDto) {
        productRatingService.updateCommentById(commentId, commentDto);
    }

    @Operation(
            summary = "Delete current comment",
            description = "Delete current comment by comment_id"
    )
    @DeleteMapping("/comments/current/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCommentById(@PathVariable("id") @Min(0) Long commentId) {
        productRatingService.deleteCommentId(commentId);
    }
    @Operation(
            summary = "Get all comments for current product",
            description = "Get all comments by pages for current product by product_id"
    )
    @GetMapping("/comments/{id}")
    public ResponseEntity<PageResponseForCommentDto> getCommentsByProductId(@PathVariable("id") @Min(0) Long productId,
                                                                            @RequestParam @Min(0) Integer offset,
                                                                            @RequestParam @Min(1) Integer limit) {
        return productRatingService.getAllCommentsByProductId(productId, offset, limit);
    }

    @Operation(
            summary = "Get all information about current Product",
            description = "Get all information (id, name, description, category_id, AVG rating," +
                    " all comments) about current product by product_id"
    )
    @GetMapping("/{id}")
    public ResponseEntity<ProductWithRatingDto> getProductWithRatingAndCommentsById(@PathVariable("id") @Min(0) Long productId) {
        return productRatingService.getProductWithAVGRatingAndCommentsByProductId(productId);
    }
}
