package com.pvxdv.consumer.api;


import com.pvxdv.consumer.dto.PageResponseForProductDto;
import com.pvxdv.consumer.dto.ProductDto;
import com.pvxdv.consumer.service.ProductService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


@RestController
@RequestMapping("api/v1/products")
@AllArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping()
    public ResponseEntity<ProductDto> createNewProduct(@RequestBody ProductDto productDTO) {
        return productService.createNewProduct(productDTO);
    }

    @GetMapping()
    public ResponseEntity<PageResponseForProductDto> getProductsByFilter(@RequestParam Optional<String> name,
                                                                         @RequestParam Optional<String> description,
                                                                         @RequestParam Optional<BigDecimal> price,
                                                                         @RequestParam Optional<Long> categoryId,
                                                                         @RequestParam @NonNull Integer offset,
                                                                         @RequestParam @NonNull Integer limit) {
        Map<String, Object> filterParams = new HashMap<>();
        filterParams.put("name", name.orElse(null));
        filterParams.put("description", description.orElse(null));
        filterParams.put("price", price.orElse(null));
        filterParams.put("categoryId", categoryId.orElse(null));
        filterParams.put("offset", offset);
        filterParams.put("limit", limit);
        return productService.getProductsByFiler(filterParams);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long id) {
        return productService.findProductById(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateProductById(@PathVariable Long id, @RequestBody ProductDto productDTO) {
        productService.updateProductById(id, productDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProductById(@PathVariable Long id) {
        productService.deleteProductById(id);
    }
}
