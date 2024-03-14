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
    private final String CATEGORY_NOT_FOUND = "Category with id=%d not found";
    private final String SAVE_CATEGORY = "Saving Category=%s";

    @Override
    public CategoryDto createNewCategory(CategoryDto categoryDTO) {
        if (categoryRepository.findCategoryByName(categoryDTO.name()).isEmpty()) {
            if (!categoryExist(categoryDTO.id())) {
                Category categoryToSave = categoryDtoToCategoryMapper.map(categoryDTO);

                log.debug(SAVE_CATEGORY.formatted(categoryToSave));

                return categoryToCategoryDtoMapper.map(categoryRepository
                        .save(categoryToSave));
            } else {
                throw new ResourceAlreadyExistException("Category with id=%d already exist"
                        .formatted(categoryDTO.id()));
            }
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
    public CategoryDto findCategoryById(Long categoryId) {
        return categoryToCategoryDtoMapper.map(categoryRepository.findById(categoryId).
                orElseThrow(() -> new ResourceNotFoundException(CATEGORY_NOT_FOUND.formatted(categoryId))));

    }

    @Override
    public void updateCategoryById(Long categoryId, CategoryDto categoryDTO) {
        if (categoryExist(categoryId)) {
            Category categoryToUpdate = categoryRepository.findById(categoryId).get();
            categoryToUpdate.setName(categoryDTO.name());

            log.debug(SAVE_CATEGORY.formatted(categoryToUpdate));

            categoryRepository.save(categoryToUpdate);
        } else {
            throw new ResourceNotFoundException(CATEGORY_NOT_FOUND.formatted(categoryId));
        }
    }

    @Override
    public void deleteCategoryById(Long categoryId) {
        if (categoryExist(categoryId)) {
            categoryRepository.deleteById(categoryId);
            log.debug("Category with id=%d delete successfully".formatted(categoryId));
        } else {
            throw new ResourceNotFoundException(CATEGORY_NOT_FOUND.formatted(categoryId));
        }
    }

    private Boolean categoryExist(Long categoryId) {
        if (categoryId != null) {
            return categoryRepository.findById(categoryId).isPresent();
        } else {
            return false;
        }
    }
}
