package com.pvxdv.supplier.service;

import com.pvxdv.supplier.model.Product;

import java.util.List;

public interface CategoryServiceImpl {
    Product createNewProduct(Product product);
    List<Product> findAllProducts();
    Product findProductById(Long id);
    Product updateProductById(Long id, Product product);
    void deleteProductById(Long id);
}
