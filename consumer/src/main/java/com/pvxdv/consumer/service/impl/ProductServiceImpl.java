package com.pvxdv.consumer.service.impl;


import com.pvxdv.consumer.dto.PageResponseForProductDto;
import com.pvxdv.consumer.dto.ProductDto;
import com.pvxdv.consumer.exception.RestTemplateException;
import com.pvxdv.consumer.service.ProductService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;


@Slf4j
@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final String CREATE_PRODUCT_ENDPOINT_URL = "http://localhost:8080/api/v1/products";
    private final String GET_PRODUCTS_ENDPOINT_URL = "http://localhost:8080/api/v1/products";
    private final String GET_PRODUCT_ENDPOINT_URL = "http://localhost:8080/api/v1/products/{id}";
    private final String UPDATE_PRODUCT_ENDPOINT_URL = "http://localhost:8080/api/v1/products/{id}";
    private final String DELETE_PRODUCT_ENDPOINT_URL = "http://localhost:8080/api/v1/products/{id}";
    private final RestTemplate restTemplate;

    @Override
    public ResponseEntity<ProductDto> createNewProduct(ProductDto productDTO) {
        try {
            return restTemplate.postForEntity(CREATE_PRODUCT_ENDPOINT_URL, productDTO, ProductDto.class);
        } catch (HttpStatusCodeException e) {
            throw new RestTemplateException(e.getMessage(), e.getStatusCode(), CREATE_PRODUCT_ENDPOINT_URL);
        }
    }

    @Override
    public ResponseEntity<PageResponseForProductDto> getProductsByFiler(Map<String, Object> filterParams) {
        try {
            return restTemplate
                    .getForEntity(GET_PRODUCTS_ENDPOINT_URL, PageResponseForProductDto.class, filterParams);
        } catch (HttpStatusCodeException e) {
            throw new RestTemplateException(e.getMessage(), e.getStatusCode(), GET_PRODUCTS_ENDPOINT_URL);
        }
    }

    @Override
    public ResponseEntity<ProductDto> findProductById(Long id) {
        try {
            return restTemplate.getForEntity(GET_PRODUCT_ENDPOINT_URL, ProductDto.class, id);
        } catch (HttpStatusCodeException e) {
            throw new RestTemplateException(e.getMessage(), e.getStatusCode(), GET_PRODUCT_ENDPOINT_URL);
        }
    }

    @Override
    public void updateProductById(Long id, ProductDto productDTO) {
        try {
            restTemplate.put(UPDATE_PRODUCT_ENDPOINT_URL, productDTO, id);
        } catch (HttpStatusCodeException e) {
            throw new RestTemplateException(e.getMessage(), e.getStatusCode(), UPDATE_PRODUCT_ENDPOINT_URL);
        }

    }

    @Override
    public void deleteProductById(Long id) {
        try {
            restTemplate.delete(DELETE_PRODUCT_ENDPOINT_URL, id);
        } catch (HttpStatusCodeException e) {
            throw new RestTemplateException(e.getMessage(), e.getStatusCode(), DELETE_PRODUCT_ENDPOINT_URL);
        }
    }
}
