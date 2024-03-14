package com.pvxdv.supplier.mapper.impl;

import com.pvxdv.supplier.dto.ProductDto;
import com.pvxdv.supplier.exception.ResourceNotFoundException;
import com.pvxdv.supplier.mapper.Mapper;
import com.pvxdv.supplier.model.Category;
import com.pvxdv.supplier.model.Product;
import com.pvxdv.supplier.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ProductDtoToProductMapper implements Mapper<ProductDto, Product> {

    private final CategoryRepository categoryRepository;

    @Override
    public Product map(ProductDto productDto) {
        Product product = new Product();
        product.setId(productDto.id());
        product.setName(productDto.name());
        product.setDescription(productDto.description());
        product.setPrice(productDto.price());
        product.setCategory(getCategory(productDto.categoryId()));
        return product;
    }

    private Category getCategory(Long id) {
        return Optional.ofNullable(id)
                .flatMap(categoryRepository::findById)
                .orElseThrow(() -> new ResourceNotFoundException("Category with id=%d not found".formatted(id)));
    }
}
