package com.pvxdv.supplier.service;

import com.pvxdv.supplier.dto.ProductDto;
import com.pvxdv.supplier.util.searchFilter.ProductFilter;
import org.springframework.data.domain.Page;

public interface ProductService {
    ProductDto createNewProduct(ProductDto productDTO);

    ProductDto findProductById(Long id);

    void updateProductById(Long id, ProductDto productDTO);

    void deleteProductById(Long id);

    Page<ProductDto> getProductsByFiler(ProductFilter productFilterDTO);
}
