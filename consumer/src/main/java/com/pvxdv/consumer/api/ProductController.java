package com.pvxdv.consumer.api;


import com.pvxdv.consumer.dto.PageResponseDto;
import com.pvxdv.consumer.dto.ProductDto;
import com.pvxdv.consumer.service.ProductService;
import com.pvxdv.consumer.util.searchFilter.ProductFilter;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Optional;


@RestController
@RequestMapping("api/v1/products")
@AllArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping()
    public ResponseEntity<ProductDto> createNewProduct(@RequestBody ProductDto productDTO) {
        return new ResponseEntity<>(productService.createNewProduct(productDTO), HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<PageResponseDto<ProductDto>> getProductsByFilter(@RequestParam Optional<String> name,
                                                                           @RequestParam Optional<String> description,
                                                                           @RequestParam Optional<BigDecimal> price,
                                                                           @RequestParam Optional<Long> categoryId,
                                                                           @RequestParam Integer offset,
                                                                           @RequestParam Integer limit) {
        ProductFilter productFilter = new ProductFilter(name.orElse(null), description.orElse(null),
                price.orElse(null), categoryId.orElse(null), offset, limit);
        return new ResponseEntity<>(productService.getProductsByFiler(productFilter), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long id) {
        return new ResponseEntity<>(productService.findProductById(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> updateProductById(@PathVariable Long id, @RequestBody ProductDto productDTO) {
        return new ResponseEntity<>(productService.updateProductById(id, productDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProductById(@PathVariable Long id) {
        productService.deleteProductById(id);
    }
}
