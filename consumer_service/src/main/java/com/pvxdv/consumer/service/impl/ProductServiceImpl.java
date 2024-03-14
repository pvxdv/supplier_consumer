package com.pvxdv.consumer.service.impl;


import com.pvxdv.consumer.dto.PageResponseForProductDto;
import com.pvxdv.consumer.dto.ProductDto;
import com.pvxdv.consumer.error.ErrorMessage;
import com.pvxdv.consumer.exception.RestTemplateException;
import com.pvxdv.consumer.service.ProductService;
import com.pvxdv.consumer.util.searchFilter.ProductFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;


@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    @Value("${service}")
    private String supplierServiceUrl;
    private final RestTemplate restTemplate;

    @Override
    public ResponseEntity<ProductDto> createNewProduct(ProductDto productDTO) {
        try {
            return restTemplate.postForEntity("%s/api/v1/products".formatted(supplierServiceUrl), productDTO, ProductDto.class);
        } catch (HttpStatusCodeException e) {
            throw new RestTemplateException(e.getResponseBodyAs(ErrorMessage.class).getMessage(),
                    e.getStatusCode(), "%s/api/v1/products".formatted(supplierServiceUrl));
        }
    }

    @Override
    public ResponseEntity<PageResponseForProductDto> getProductsByFiler(ProductFilter productFilter) {
        String urlTemplate = UriComponentsBuilder.fromHttpUrl("%s/api/v1/products".formatted(supplierServiceUrl))
                .queryParam("name", productFilter.name())
                .queryParam("description", productFilter.description())
                .queryParam("price", productFilter.price())
                .queryParam("categoryId", productFilter.categoryId())
                .queryParam("offset", productFilter.offset())
                .queryParam("limit", productFilter.limit())
                .encode()
                .toUriString();

        try {
            return restTemplate
                    .getForEntity(urlTemplate, PageResponseForProductDto.class);
        } catch (HttpStatusCodeException e) {
            throw new RestTemplateException(e.getResponseBodyAs(ErrorMessage.class).getMessage(),
                    e.getStatusCode(), urlTemplate);
        }
    }

    @Override
    public ResponseEntity<ProductDto> findProductById(Long id) {
        try {
            return restTemplate.getForEntity("%s/api/v1/products/{id}".formatted(supplierServiceUrl), ProductDto.class, id);
        } catch (HttpStatusCodeException e) {
            throw new RestTemplateException(e.getResponseBodyAs(ErrorMessage.class).getMessage(),
                    e.getStatusCode(), "%s/api/v1/products/{id}".formatted(supplierServiceUrl));
        }
    }

    @Override
    public void updateProductById(Long id, ProductDto productDTO) {
        try {
            restTemplate.put("%s/api/v1/products/{id}".formatted(supplierServiceUrl), productDTO, id);
        } catch (HttpStatusCodeException e) {
            throw new RestTemplateException(e.getResponseBodyAs(ErrorMessage.class).getMessage(),
                    e.getStatusCode(), "%s/api/v1/products/{id}".formatted(supplierServiceUrl));
        }

    }

    @Override
    public void deleteProductById(Long id) {
        try {
            restTemplate.delete("%s/api/v1/products/{id}".formatted(supplierServiceUrl), id);
        } catch (HttpStatusCodeException e) {
            throw new RestTemplateException(e.getResponseBodyAs(ErrorMessage.class).getMessage(),
                    e.getStatusCode(), "%s/api/v1/products/{id}".formatted(supplierServiceUrl));
        }
    }
}
