package com.pvxdv.supplier.mapper.impl;

import com.pvxdv.supplier.dto.ProductDto;
import com.pvxdv.supplier.mapper.Mapper;
import com.pvxdv.supplier.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductToProductDtoMapper implements Mapper<Product, ProductDto> {
    @Override
    public ProductDto map(Product product) {
        return new ProductDto(product.getId(), product.getName(), product.getDescription(), product.getPrice(), product.getCategory().getId());
    }
}
