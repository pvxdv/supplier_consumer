package com.pvxdv.supplier.service;

import com.pvxdv.supplier.model.Category;

import java.util.List;

public interface CategoryService {
    Category createNewCategory(Category category);
    List<Category> findAllCategories();
    Category findCategoryById(Long id);
    Category updateCategoryById(Long id, Category category);
    void deleteCategoryById(Long id);
}
