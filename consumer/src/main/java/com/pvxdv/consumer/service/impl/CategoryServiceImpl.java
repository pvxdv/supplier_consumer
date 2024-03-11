package com.pvxdv.consumer.service.impl;


import com.pvxdv.consumer.dto.CategoryDto;
import com.pvxdv.consumer.dto.CategoryListDto;
import com.pvxdv.consumer.exception.UnexpectedHTTPStatusException;
import com.pvxdv.consumer.service.CategoryService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Slf4j
@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final String CREATE_CATEGORY_ENDPOINT_URL = "http://localhost:8080/api/v1/categories";
    private final String GET_CATEGORIES_ENDPOINT_URL = "http://localhost:8080/api/v1/categories";
    private final String GET_CATEGORY_ENDPOINT_URL = "http://localhost:8080/api/v1/categories/{id}";
    private final String UPDATE_CATEGORY_ENDPOINT_URL = "http://localhost:8080/api/v1/categories/{id}";
    private final String DELETE_CATEGORY_ENDPOINT_URL = "http://localhost:8080/api/v1/categories/{id}";
    private final RestTemplate restTemplate;

    @Override
    public ResponseEntity<CategoryDto> createNewCategory(CategoryDto categoryDTO) {
        ResponseEntity<CategoryDto> response = restTemplate
                .postForEntity(CREATE_CATEGORY_ENDPOINT_URL, categoryDTO, CategoryDto.class);
        if (response.getStatusCode().is2xxSuccessful()) {
            return response;
        } else {
            throw new UnexpectedHTTPStatusException(Objects.requireNonNull(response.getBody()).toString(),
                    response.getStatusCode().toString());
        }
    }

    @Override
    public ResponseEntity<CategoryListDto> findAllCategories() {
        ResponseEntity<CategoryListDto> response = restTemplate
                .getForEntity(GET_CATEGORIES_ENDPOINT_URL, CategoryListDto.class);
        if (response.getStatusCode().is2xxSuccessful()) {
            return response;
        } else {
            throw new UnexpectedHTTPStatusException(Objects.requireNonNull(response.getBody()).toString(),
                    response.getStatusCode().toString());
        }
    }

    @Override
    public ResponseEntity<CategoryDto> findCategoryById(Long id) {
        ResponseEntity<CategoryDto> response = restTemplate
                .getForEntity(GET_CATEGORY_ENDPOINT_URL, CategoryDto.class, id);
        if (response.getStatusCode().is2xxSuccessful()) {
            return response;
        } else {
            throw new UnexpectedHTTPStatusException(Objects.requireNonNull(response.getBody()).toString(),
                    response.getStatusCode().toString());
        }
    }

    @Override
    public void updateCategoryById(Long id, CategoryDto categoryDTO) {
        restTemplate.put(UPDATE_CATEGORY_ENDPOINT_URL, categoryDTO, id);
    }

    @Override
    public void deleteCategoryById(Long id) {
        restTemplate.delete(DELETE_CATEGORY_ENDPOINT_URL, id);
    }
}
