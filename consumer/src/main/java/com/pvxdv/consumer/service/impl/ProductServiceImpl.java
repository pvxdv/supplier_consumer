package com.pvxdv.consumer.service.impl;


import com.pvxdv.consumer.dto.PageResponseDto;
import com.pvxdv.consumer.dto.ProductDto;
import com.pvxdv.consumer.service.ProductService;
import com.pvxdv.consumer.util.searchFilter.ProductFilter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;



@Slf4j
@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    @Override
    public ResponseEntity<ProductDto> createNewProduct(ProductDto productDTO) {
        return null;
    }

    @Override
    public ResponseEntity<PageResponseDto<ProductDto>> getProductsByFiler(ProductFilter filterDTO) {
      return null;
    }

    @Override
    public ResponseEntity<ProductDto> findProductById(Long id) {
        return null;
    }

    @Override
    public void updateProductById(Long id, ProductDto productDTO) {

    }

    @Override
    public void deleteProductById(Long id) {

    }
}
