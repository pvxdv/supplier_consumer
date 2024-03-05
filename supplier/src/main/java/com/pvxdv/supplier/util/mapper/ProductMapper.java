package com.pvxdv.supplier.util.mapper;

import com.pvxdv.supplier.dto.ProductDTO;
import com.pvxdv.supplier.model.Category;
import com.pvxdv.supplier.model.Product;

public class ProductMapper {
    public static ProductDTO productToProductDTO(Product product){
        return ProductDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .categoryId(product.getCategory().getId())
                .build();
    }

    public static Product productDTOToProduct(ProductDTO productDTO, Category category){
        return Product.builder()
                .id(productDTO.getId())
                .name(productDTO.getName())
                .description(productDTO.getDescription())
                .price(productDTO.getPrice())
                .category(category)
                .build();
    }
}
