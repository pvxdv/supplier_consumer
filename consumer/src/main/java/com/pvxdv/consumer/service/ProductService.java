package com.pvxdv.consumer.service;


import com.pvxdv.consumer.dto.PageResponseForProductDto;
import com.pvxdv.consumer.dto.ProductDto;
import com.pvxdv.consumer.util.searchFilter.ProductFilter;
import org.springframework.http.ResponseEntity;


public interface ProductService {
    ResponseEntity<ProductDto> createNewProduct(ProductDto productDTO);

    ResponseEntity<ProductDto> findProductById(Long id);

    void updateProductById(Long id, ProductDto productDTO);

    void deleteProductById(Long id);

    ResponseEntity<PageResponseForProductDto> getProductsByFiler(ProductFilter productFilter);
}
