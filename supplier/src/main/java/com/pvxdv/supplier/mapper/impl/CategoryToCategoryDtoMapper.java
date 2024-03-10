package com.pvxdv.supplier.mapper.impl;

import com.pvxdv.supplier.dto.CategoryDto;
import com.pvxdv.supplier.mapper.Mapper;
import com.pvxdv.supplier.model.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryToCategoryDtoMapper implements Mapper<Category, CategoryDto> {
    @Override
    public CategoryDto map(Category category) {
        return new CategoryDto(category.getId(), category.getName());
    }
}
