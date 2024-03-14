package com.pvxdv.supplier.service;

import com.pvxdv.supplier.dto.CategoryDto;

import java.util.List;

public interface CategoryService {
    CategoryDto createNewCategory(CategoryDto categoryDTO);

    List<CategoryDto> findAllCategories();

    CategoryDto findCategoryById(Long categoryId);

    void updateCategoryById(Long categoryId, CategoryDto categoryDTO);

    void deleteCategoryById(Long categoryId);
}
