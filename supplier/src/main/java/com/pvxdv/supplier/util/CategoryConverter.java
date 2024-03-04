package com.pvxdv.supplier.util;

import com.pvxdv.supplier.dto.CategoryDTO;
import com.pvxdv.supplier.model.Category;

public class CategoryConverter {
    public static Category categoryDTOToCategory(CategoryDTO categoryDTO){
        return Category.builder()
                .id(categoryDTO.getId())
                .name(categoryDTO.getName())
                .build();
    }

    public static CategoryDTO categoryToCategoryDTO(Category category){
        return CategoryDTO.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }
}
