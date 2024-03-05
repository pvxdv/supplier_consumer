package com.pvxdv.supplier.service.impl;

import com.pvxdv.supplier.dto.CategoryDTO;
import com.pvxdv.supplier.exception.ResourceAlreadyExistException;
import com.pvxdv.supplier.exception.ResourceNotFoundException;
import com.pvxdv.supplier.model.Category;
import com.pvxdv.supplier.repository.CategoryRepository;
import com.pvxdv.supplier.service.CategoryService;
import com.pvxdv.supplier.util.filterSearch.CategoryPredicateBuilder;
import com.pvxdv.supplier.util.mapper.CategoryMapper;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final String categoryNotFound = "Category with id=%d not found";


    @Override
    public CategoryDTO createNewCategory(CategoryDTO categoryDTO) {
        if (categoryRepository.findCategoryByName(categoryDTO.getName()).isEmpty()) {
            Category categoryToSave = CategoryMapper.categoryDTOToCategory(categoryDTO);
            log.debug("Added new Category = %s".formatted(categoryToSave));

            return CategoryMapper.categoryToCategoryDTO(categoryRepository.save(categoryToSave));
        }
        throw new ResourceAlreadyExistException("Category with name=%s already exist".formatted(categoryDTO.getName()));
    }

    @Override
    public List<CategoryDTO> findAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        log.debug("Find all categories =%s".formatted(categories));
        List<CategoryDTO> response = new ArrayList<>(categories.size());

        for (Category category : categories) {
            response.add(CategoryMapper.categoryToCategoryDTO(category));
        }

        return response;
    }

    @Override
    public CategoryDTO findCategoryById(Long id) {
        if (categoryExist(id)) {
            return CategoryMapper.categoryToCategoryDTO(categoryRepository.findById(id).get());
        }
        throw new ResourceNotFoundException(categoryNotFound.formatted(id));
    }

    @Override
    public CategoryDTO updateCategoryById(Long id, CategoryDTO categoryDTO) {
        if (categoryExist(id)) {
            Category categoryToUpdate = categoryRepository.findById(id).get();
            categoryToUpdate.setName(categoryDTO.getName());
            log.debug("Category with id=%d update successfully =%s".formatted(id, categoryToUpdate));
            return CategoryMapper.categoryToCategoryDTO(categoryRepository.save(categoryToUpdate));
        }
        throw new ResourceNotFoundException(categoryNotFound.formatted(id));
    }

    @Override
    public void deleteCategoryById(Long id) {
        if (categoryExist(id)) {
            categoryRepository.deleteById(id);
            log.debug("Category with id=%d delete successfully".formatted(id));
        }
        throw new ResourceNotFoundException(categoryNotFound.formatted(id));
    }

    @Override
    public List<CategoryDTO> searchCategories(String search) {
        CategoryPredicateBuilder builder = new CategoryPredicateBuilder();
        if (search != null) {
            Pattern pattern = Pattern.compile("(\\w+?)([:<>])(\\w+?),");
            Matcher matcher = pattern.matcher(search + ",");
            while (matcher.find()) {
                builder.with(matcher.group(1), matcher.group(2), matcher.group(3));
            }
        }
        BooleanExpression expression = builder.build();
        Iterable<Category> categories;

        if (expression != null) {
            categories = categoryRepository.findAll(expression);
        } else {
            categories = categoryRepository.findAll();
        }

        log.debug("Find all categories =%s".formatted(categories));
        List<CategoryDTO> response = new LinkedList<>();

        for (Category category : categories) {
            response.add(CategoryMapper.categoryToCategoryDTO(category));
        }
        return response;
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
