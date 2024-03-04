package com.pvxdv.supplier.service;

import com.pvxdv.supplier.dto.ProductDTO;

import java.util.List;

public interface ProductService {
    ProductDTO createNewProduct(ProductDTO productDTO);
    List<ProductDTO> findAllProducts();
    ProductDTO findProductById(Long id);
    ProductDTO updateProductById(Long id, ProductDTO productDTO);
    void deleteProductById(Long id);
}
