package com.pvxdv.supplier.service.impl;

import com.pvxdv.supplier.model.Product;
import com.pvxdv.supplier.repository.CategoryRepository;
import com.pvxdv.supplier.repository.ProductRepository;
import com.pvxdv.supplier.service.CategoryServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements CategoryServiceImpl {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public Product createNewProduct(Product product) {
        if(categoryRepository.findCategoryByName(product.getCategory().getName()).isPresent()){
            return productRepository.save(product);
        }
        //todo exception
        return null;
    }

    @Override
    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product findProductById(Long id) {
        if(productExist(id)){
            return productRepository.findById(id).get();
        }
        //todo exception
        return null;
    }

    @Override
    public Product updateProductById(Long id, Product product) {
        if(productExist(id)){
            return productRepository.updateProductById(id, product);
        }
        //todo exception
        return null;
    }

    @Override
    public void deleteProductById(Long id) {
        if(productExist(id)){
            productRepository.deleteById(id);
        }
        //todo exception
    }

    private Boolean productExist(Long id) {
        return productRepository.findById(id).isPresent();
    }
}
