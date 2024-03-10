package com.pvxdv.consumer.service;


import com.pvxdv.consumer.dto.CategoryDto;

import java.util.List;

public interface CategoryService {
    CategoryDto createNewCategory(CategoryDto categoryDTO);

    List<CategoryDto> findAllCategories();

    CategoryDto findCategoryById(Long id);

    CategoryDto updateCategoryById(Long id, CategoryDto categoryDTO);

    void deleteCategoryById(Long id);
}
