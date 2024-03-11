package com.pvxdv.consumer.service;



import com.pvxdv.consumer.dto.PageResponseForProductDto;
import com.pvxdv.consumer.dto.ProductDto;
import org.springframework.http.ResponseEntity;

import java.util.Map;


public interface ProductService {
    ResponseEntity<ProductDto> createNewProduct(ProductDto productDTO);

    ResponseEntity<ProductDto> findProductById(Long id);

    void updateProductById(Long id, ProductDto productDTO);

    void deleteProductById(Long id);

    ResponseEntity<PageResponseForProductDto> getProductsByFiler(Map<String, Object> filterParams);
}
