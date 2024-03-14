package com.pvxdv.consumer.api;


import com.pvxdv.consumer.dto.PageResponseForProductDto;
import com.pvxdv.consumer.dto.ProductDto;
import com.pvxdv.consumer.service.ProductService;
import com.pvxdv.consumer.util.searchFilter.ProductFilter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Optional;


@Tag(name = "ProductController", description = "CRUD operations with Product Entity")
@RestController
@RequestMapping("api/v1/products")
@AllArgsConstructor
public class ProductController {
    private final ProductService productService;

    @Operation(
            summary = "Create new product",
            description = "Create new product"
    )
    @PostMapping()
    public ResponseEntity<ProductDto> createNewProduct(@RequestBody ProductDto productDTO) {
        return productService.createNewProduct(productDTO);
    }

    @Operation(
            summary = "Get all products with filtration and pagination",
            description = "Allow filter by name, description (accepts a regular expression), " +
                    "by price (shows products with prices below the one passed in the request parameter), " +
                    "and by category_id."
    )
    @GetMapping()
    public ResponseEntity<PageResponseForProductDto> getProductsByFilter(@RequestParam Optional<String> name,
                                                                         @RequestParam Optional<String> description,
                                                                         @RequestParam Optional<BigDecimal> price,
                                                                         @RequestParam Optional<Long> categoryId,
                                                                         @RequestParam Integer offset,
                                                                         @RequestParam Integer limit) {
        ProductFilter productFilter = new ProductFilter(name.orElse(null), description.orElse(null),
                price.orElse(null), categoryId.orElse(null), offset, limit);

        return productService.getProductsByFiler(productFilter);
    }

    @Operation(
            summary = "Get current product",
            description = "Get current product by product_id"
    )
    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long id) {
        return productService.findProductById(id);
    }

    @Operation(
            summary = "Update current product",
            description = "Update current product by product_id"
    )
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateProductById(@PathVariable Long id, @RequestBody ProductDto productDTO) {
        productService.updateProductById(id, productDTO);
    }

    @Operation(
            summary = "Delete current product",
            description = "Delete current product by product_id"
    )
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProductById(@PathVariable Long id) {
        productService.deleteProductById(id);
    }
}
