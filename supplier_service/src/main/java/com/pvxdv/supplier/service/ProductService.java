package com.pvxdv.supplier.service;

import com.pvxdv.supplier.dto.ProductDto;
import com.pvxdv.supplier.util.searchFilter.ProductFilter;
import org.springframework.data.domain.Page;

public interface ProductService {
    ProductDto createNewProduct(ProductDto productDTO);

    ProductDto findProductById(Long productId);

    void updateProductById(Long productId, ProductDto productDTO);

    void deleteProductById(Long productId);

    Page<ProductDto> getProductsByFiler(ProductFilter productFilterDTO);
}
