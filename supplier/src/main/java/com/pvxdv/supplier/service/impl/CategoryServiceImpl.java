package com.pvxdv.supplier.service.impl;

import com.pvxdv.supplier.dto.CategoryDto;
import com.pvxdv.supplier.exception.ResourceAlreadyExistException;
import com.pvxdv.supplier.exception.ResourceNotFoundException;
import com.pvxdv.supplier.mapper.impl.CategoryDtoToCategoryMapper;
import com.pvxdv.supplier.mapper.impl.CategoryToCategoryDtoMapper;
import com.pvxdv.supplier.model.Category;
import com.pvxdv.supplier.repository.CategoryRepository;
import com.pvxdv.supplier.service.CategoryService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryToCategoryDtoMapper categoryToCategoryDtoMapper;
    private final CategoryDtoToCategoryMapper categoryDtoToCategoryMapper;
    private final String categoryNotFound = "Category with id=%d not found";

    @Override
    public CategoryDto createNewCategory(CategoryDto categoryDTO) {
        if (!categoryExist(categoryDTO.id())) {
            return categoryToCategoryDtoMapper.map(categoryRepository
                    .save(categoryDtoToCategoryMapper.map(categoryDTO)));
        } else {
            throw new ResourceAlreadyExistException("Category with name=%s already exist"
                    .formatted(categoryDTO.name()));
        }
    }

    @Override
    public List<CategoryDto> findAllCategories() {
        return categoryRepository.findAll().stream()
                .map(categoryToCategoryDtoMapper::map)
                .toList();
    }

    @Override
    public CategoryDto findCategoryById(Long id) {
        return categoryToCategoryDtoMapper.map(categoryRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException(categoryNotFound.formatted(id))));

    }

    @Override
    public CategoryDto updateCategoryById(Long id, CategoryDto categoryDTO) {
        if (categoryExist(id)) {
            Category categoryToUpdate = categoryRepository.findById(id).get();

            if (categoryDTO.name() != null && !categoryDTO.name().isBlank()) {
                categoryToUpdate.setName(categoryDTO.name());
            }

            log.debug("Category with id=%d update successfully =%s".formatted(id, categoryToUpdate));
            return categoryToCategoryDtoMapper.map(categoryRepository.save(categoryToUpdate));
        } else {
            throw new ResourceNotFoundException(categoryNotFound.formatted(id));
        }
    }

    @Override
    public void deleteCategoryById(Long id) {
        if (categoryExist(id)) {
            categoryRepository.deleteById(id);
            log.debug("Category with id=%d delete successfully".formatted(id));
        } else {
            throw new ResourceNotFoundException(categoryNotFound.formatted(id));
        }
    }

    private Boolean categoryExist(Long id) {
        if (id != null) {
            return categoryRepository.findById(id).isPresent();
        } else {
            return false;
        }
    }
}
