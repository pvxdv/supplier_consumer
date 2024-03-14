package com.pvxdv.consumer.service;


import com.pvxdv.consumer.dto.CategoryDto;
import com.pvxdv.consumer.dto.CategoryListDto;
import org.springframework.http.ResponseEntity;

public interface CategoryService {
    ResponseEntity<CategoryDto> createNewCategory(CategoryDto categoryDTO);

    ResponseEntity<CategoryListDto> findAllCategories();

    ResponseEntity<CategoryDto> findCategoryById(Long id);

    void updateCategoryById(Long id, CategoryDto categoryDTO);

    void deleteCategoryById(Long id);
}
