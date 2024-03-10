package com.pvxdv.consumer.service.impl;


import com.pvxdv.consumer.dto.CategoryDto;
import com.pvxdv.consumer.service.CategoryService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {
        @Override
    public CategoryDto createNewCategory(CategoryDto categoryDTO) {
        return null;
    }

    @Override
    public List<CategoryDto> findAllCategories() {
       return null;
    }

    @Override
    public CategoryDto findCategoryById(Long id) {
            return null;
    }

    @Override
    public CategoryDto updateCategoryById(Long id, CategoryDto categoryDTO) {
        return null;
    }

    @Override
    public void deleteCategoryById(Long id) {

    }
}
