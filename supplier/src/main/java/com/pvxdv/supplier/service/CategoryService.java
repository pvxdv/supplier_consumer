package com.pvxdv.supplier.service;

import com.pvxdv.supplier.dto.CategoryDTO;

import java.util.List;

public interface CategoryService {
    CategoryDTO createNewCategory(CategoryDTO categoryDTO);
    List<CategoryDTO> findAllCategories();
    CategoryDTO findCategoryById(Long id);
    CategoryDTO updateCategoryById(Long id, CategoryDTO categoryDTO);
    void deleteCategoryById(Long id);
}
