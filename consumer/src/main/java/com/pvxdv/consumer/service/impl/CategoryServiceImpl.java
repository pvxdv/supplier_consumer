package com.pvxdv.consumer.service.impl;


import com.pvxdv.consumer.dto.CategoryDto;
import com.pvxdv.consumer.dto.CategoryListDto;
import com.pvxdv.consumer.exception.RestTemplateException;
import com.pvxdv.consumer.service.CategoryService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

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
        try {
            return restTemplate.postForEntity(CREATE_CATEGORY_ENDPOINT_URL, categoryDTO, CategoryDto.class);
        } catch (HttpStatusCodeException e) {
            throw new RestTemplateException(e.getMessage(), e.getStatusCode(), CREATE_CATEGORY_ENDPOINT_URL);
        }
    }

    @Override
    public ResponseEntity<CategoryListDto> findAllCategories() {
        try {
            return restTemplate.getForEntity(GET_CATEGORIES_ENDPOINT_URL, CategoryListDto.class);
        } catch (HttpStatusCodeException e) {
            throw new RestTemplateException(e.getMessage(), e.getStatusCode(), GET_CATEGORIES_ENDPOINT_URL);
        }
    }

    @Override
    public ResponseEntity<CategoryDto> findCategoryById(Long id) {
        try {
            return restTemplate.getForEntity(GET_CATEGORY_ENDPOINT_URL, CategoryDto.class, id);
        } catch (HttpStatusCodeException e) {
            throw new RestTemplateException(e.getMessage(), e.getStatusCode(), GET_CATEGORY_ENDPOINT_URL);
        }
    }

    @Override
    public void updateCategoryById(Long id, CategoryDto categoryDTO) {
        try {
            restTemplate.put(UPDATE_CATEGORY_ENDPOINT_URL, categoryDTO, id);
        } catch (HttpStatusCodeException e) {
            throw new RestTemplateException(e.getMessage(), e.getStatusCode(), UPDATE_CATEGORY_ENDPOINT_URL);
        }
    }

    @Override
    public void deleteCategoryById(Long id) {
        try {
            restTemplate.delete(DELETE_CATEGORY_ENDPOINT_URL, id);
        } catch (HttpStatusCodeException e) {
            throw new RestTemplateException(e.getMessage(), e.getStatusCode(), DELETE_CATEGORY_ENDPOINT_URL);
        }
    }
}
