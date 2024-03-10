package com.pvxdv.supplier.mapper.impl;

import com.pvxdv.supplier.dto.CategoryDto;
import com.pvxdv.supplier.mapper.Mapper;
import com.pvxdv.supplier.model.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryDtoToCategoryMapper implements Mapper<CategoryDto, Category> {
    @Override
    public Category map(CategoryDto categoryDto) {
        return new Category(categoryDto.id(), categoryDto.name());
    }
}
