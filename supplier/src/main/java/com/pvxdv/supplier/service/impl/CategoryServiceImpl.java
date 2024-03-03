package com.pvxdv.supplier.service.impl;

import com.pvxdv.supplier.model.Category;
import com.pvxdv.supplier.repository.CategoryRepository;
import com.pvxdv.supplier.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    @Override
    public Category createNewCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public List<Category> findAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category findCategoryById(Long id) {
        if(categoryExist(id)){
            return categoryRepository.findById(id).get();
        }
        //todo exception
        return null;

    }

    @Override
    public Category updateCategoryById(Long id, Category category) {
        if(categoryExist(id)){
            categoryRepository.updateCategoryById(id, category);
        }
        //todo exception
        return null;
    }

    @Override
    public void deleteCategoryById(Long id) {
        if(categoryExist(id)){
            categoryRepository.deleteById(id);
        }
        //todo exception
    }

    private Boolean categoryExist(Long id) {
        return categoryRepository.findById(id).isPresent();
    }
}
