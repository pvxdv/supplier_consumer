package com.pvxdv.consumer.service.impl;

import com.pvxdv.consumer.dto.CommentDto;
import com.pvxdv.consumer.dto.PageResponseForCommentDto;
import com.pvxdv.consumer.dto.ProductWithRatingDto;
import com.pvxdv.consumer.error.ErrorMessage;
import com.pvxdv.consumer.exception.RestTemplateException;
import com.pvxdv.consumer.service.ProductRatingService;
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
public class ProductRatingServiceImpl implements ProductRatingService {
    @Value("${service}")
    private String supplierServiceUrl;
    private final RestTemplate restTemplate;

    @Override
    public ResponseEntity<CommentDto> createNewComment(CommentDto commentDto) {
        try {
            return restTemplate.postForEntity("%s/api/v1/products/rating/comments"
                    .formatted(supplierServiceUrl), commentDto, CommentDto.class);
        } catch (HttpStatusCodeException e) {
            throw new RestTemplateException(e.getResponseBodyAs(ErrorMessage.class).getMessage(),
                    e.getStatusCode(), "%s/api/v1/products/rating/comments".formatted(supplierServiceUrl));
        }
    }

    @Override
    public ResponseEntity<CommentDto> findCommentById(Long commentId) {
        try {
            return restTemplate.getForEntity("%s/api/v1/products/rating/comments/current/{id}".
                    formatted(supplierServiceUrl), CommentDto.class, commentId);
        } catch (HttpStatusCodeException e) {
            throw new RestTemplateException(e.getResponseBodyAs(ErrorMessage.class).getMessage(),
                    e.getStatusCode(), "%s/api/v1/products/rating/comments/current/{id}".
                    formatted(supplierServiceUrl));
        }
    }

    @Override
    public void updateCommentById(Long commentId, CommentDto commentDto) {
        try {
            restTemplate.put("%s/api/v1/products/rating/comments/current/{id}"
                    .formatted(supplierServiceUrl), commentDto, commentId);
        } catch (HttpStatusCodeException e) {
            throw new RestTemplateException(e.getResponseBodyAs(ErrorMessage.class).getMessage(),
                    e.getStatusCode(), "%s/api/v1/products/rating/comments/current/{id}".formatted(supplierServiceUrl));
        }
    }

    @Override
    public void deleteCommentId(Long commentId) {
        try {
            restTemplate.delete("%s/api/v1/products/rating/comments/current/{id}"
                    .formatted(supplierServiceUrl), commentId);
        } catch (HttpStatusCodeException e) {
            throw new RestTemplateException(e.getResponseBodyAs(ErrorMessage.class).getMessage(),
                    e.getStatusCode(), "%s/api/v1/products/rating/comments/current/{id}".formatted(supplierServiceUrl));
        }
    }

    @Override
    public ResponseEntity<PageResponseForCommentDto> getAllCommentsByProductId(Long productId, Integer offset, Integer limit) {
        String urlTemplate = UriComponentsBuilder.fromHttpUrl("%s/api/v1/products/rating/comments/%s"
                        .formatted(supplierServiceUrl, productId).formatted(productId))
                .queryParam("offset", offset)
                .queryParam("limit", limit)
                .encode()
                .toUriString();
        try {
            return restTemplate
                    .getForEntity(urlTemplate, PageResponseForCommentDto.class);
        } catch (HttpStatusCodeException e) {
            throw new RestTemplateException(e.getResponseBodyAs(ErrorMessage.class).getMessage(),
                    e.getStatusCode(), urlTemplate);
        }
    }

    @Override
    public ResponseEntity<ProductWithRatingDto> getProductWithAVGRatingAndCommentsByProductId(Long commentId) {
        try {
            return restTemplate.getForEntity("%s/api/v1/products/rating/{id}"
                    .formatted(supplierServiceUrl), ProductWithRatingDto.class, commentId);
        } catch (HttpStatusCodeException e) {
            throw new RestTemplateException(e.getResponseBodyAs(ErrorMessage.class).getMessage(),
                    e.getStatusCode(), "%s/api/v1/products/rating/{id}".formatted(supplierServiceUrl));
        }
    }
}
