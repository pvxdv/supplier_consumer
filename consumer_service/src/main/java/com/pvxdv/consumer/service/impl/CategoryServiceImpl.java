package com.pvxdv.consumer.service.impl;


import com.pvxdv.consumer.dto.CategoryDto;
import com.pvxdv.consumer.dto.CategoryListDto;
import com.pvxdv.consumer.error.ErrorMessage;
import com.pvxdv.consumer.exception.RestTemplateException;
import com.pvxdv.consumer.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    @Value("${service}")
    private String supplier_service_url;
    private final RestTemplate restTemplate;

    @Override
    public ResponseEntity<CategoryDto> createNewCategory(CategoryDto categoryDTO) {
        try {
            return restTemplate.postForEntity("%s/api/v1/categories"
                    .formatted(supplier_service_url), categoryDTO, CategoryDto.class);
        } catch (HttpStatusCodeException e) {
            throw new RestTemplateException(e.getResponseBodyAs(ErrorMessage.class).getMessage(),
                    e.getStatusCode(), "%s/api/v1/categories".formatted(supplier_service_url));
        }
    }


    @Override
    public ResponseEntity<CategoryListDto> findAllCategories() {
        try {
            return restTemplate.getForEntity("%s/api/v1/categories"
                    .formatted(supplier_service_url), CategoryListDto.class);
        } catch (HttpStatusCodeException e) {
            throw new RestTemplateException(e.getResponseBodyAs(ErrorMessage.class).getMessage(),
                    e.getStatusCode(), "%s/api/v1/categories".formatted(supplier_service_url));
        }
    }

    @Override
    public ResponseEntity<CategoryDto> findCategoryById(Long id) {
        try {
            return restTemplate.getForEntity("%s/api/v1/categories/{id}"
                    .formatted(supplier_service_url), CategoryDto.class, id);
        } catch (HttpStatusCodeException e) {
            throw new RestTemplateException(e.getResponseBodyAs(ErrorMessage.class).getMessage(),
                    e.getStatusCode(), "%s/api/v1/categories/{id}".formatted(supplier_service_url));
        }
    }

    @Override
    public void updateCategoryById(Long id, CategoryDto categoryDTO) {
        try {
            restTemplate.put("%s/api/v1/categories/{id}".formatted(supplier_service_url), categoryDTO, id);
        } catch (HttpStatusCodeException e) {
            throw new RestTemplateException(e.getResponseBodyAs(ErrorMessage.class).getMessage(),
                    e.getStatusCode(), "%s/api/v1/categories/{id}".formatted(supplier_service_url));
        }
    }

    @Override
    public void deleteCategoryById(Long id) {
        try {
            restTemplate.delete("%s/api/v1/categories/{id}".formatted(supplier_service_url), id);
        } catch (HttpStatusCodeException e) {
            throw new RestTemplateException(e.getResponseBodyAs(ErrorMessage.class).getMessage(),
                    e.getStatusCode(), "%s/api/v1/categories/{id}".formatted(supplier_service_url));
        }
    }
}
