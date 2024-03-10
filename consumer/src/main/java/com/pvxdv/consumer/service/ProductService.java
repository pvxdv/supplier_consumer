package com.pvxdv.consumer.service;


import com.pvxdv.consumer.dto.PageResponseDto;
import com.pvxdv.consumer.dto.ProductDto;
import com.pvxdv.consumer.util.searchFilter.ProductFilter;


public interface ProductService {
    ProductDto createNewProduct(ProductDto productDTO);

    ProductDto findProductById(Long id);

    ProductDto updateProductById(Long id, ProductDto productDTO);

    void deleteProductById(Long id);

    PageResponseDto<ProductDto> getProductsByFiler(ProductFilter productFilterDTO);
}
