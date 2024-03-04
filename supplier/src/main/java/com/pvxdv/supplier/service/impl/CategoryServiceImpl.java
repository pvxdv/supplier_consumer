package com.pvxdv.supplier.service.impl;

import com.pvxdv.supplier.dto.CategoryDTO;
import com.pvxdv.supplier.model.Category;
import com.pvxdv.supplier.repository.CategoryRepository;
import com.pvxdv.supplier.service.CategoryService;
import com.pvxdv.supplier.util.CategoryConverter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;


    @Override
    public CategoryDTO createNewCategory(CategoryDTO categoryDTO) {
        if (categoryRepository.findCategoryByName(categoryDTO.getName()).isEmpty()) {
            Category categoryToSave = CategoryConverter.categoryDTOToCategory(categoryDTO);
            log.debug("Added new Category = %s".formatted(categoryToSave));

            return CategoryConverter.categoryToCategoryDTO(categoryRepository.save(categoryToSave));
        }
        //todo exception
        return null;
    }

    @Override
    public List<CategoryDTO> findAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        log.debug("Find all categories =%s".formatted(categories));
        List<CategoryDTO> response = new ArrayList<>(categories.size());

        for (Category category : categories) {
            response.add(CategoryConverter.categoryToCategoryDTO(category));
        }

        return response;
    }

    @Override
    public CategoryDTO findCategoryById(Long id) {
        if (categoryExist(id)) {
            return CategoryConverter.categoryToCategoryDTO(categoryRepository.findById(id).get());
        }
        //todo exception
        return null;

    }

    @Override
    public CategoryDTO updateCategoryById(Long id, CategoryDTO categoryDTO) {
        if (categoryExist(id)) {
            Category categoryToUpdate = categoryRepository.findById(id).get();
            categoryToUpdate.setName(categoryDTO.getName());
            log.debug("Category with id=%d update successfully =%s".formatted(id, categoryToUpdate));
            return CategoryConverter.categoryToCategoryDTO(categoryRepository.save(categoryToUpdate));
        }
        //todo exception
        return null;
    }

    @Override
    public void deleteCategoryById(Long id) {
        if (categoryExist(id)) {
            categoryRepository.deleteById(id);
            log.debug("Category with id=%d delete successfully".formatted(id));
        }
        //todo exception
    }

    private Boolean categoryExist(Long id) {
        Boolean categoryExist = categoryRepository.findById(id).isPresent();
        if (categoryExist) {
            log.debug("Find Category by id=%d - success".formatted(id));
        } else {
            log.debug("Find Category by id=%d - fail".formatted(id));
        }
        return categoryExist;
    }
}
