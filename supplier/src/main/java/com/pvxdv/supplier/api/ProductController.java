package com.pvxdv.supplier.api;

import com.pvxdv.supplier.dto.PageResponseDto;
import com.pvxdv.supplier.dto.ProductDto;
import com.pvxdv.supplier.util.searchFilter.ProductFilter;
import com.pvxdv.supplier.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
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
        Page<ProductDto> page = productService.getProductsByFiler(productFilter);
        return new ResponseEntity<>(PageResponseDto.of(page), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long id) {
        return new ResponseEntity<>(productService.findProductById(id), HttpStatus.OK);
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
