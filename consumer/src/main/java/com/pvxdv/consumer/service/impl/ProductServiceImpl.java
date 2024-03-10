package com.pvxdv.consumer.service.impl;


import com.pvxdv.consumer.dto.PageResponseDto;
import com.pvxdv.consumer.dto.ProductDto;
import com.pvxdv.consumer.service.ProductService;
import com.pvxdv.consumer.util.searchFilter.ProductFilter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;



@Slf4j
@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    @Override
    public ProductDto createNewProduct(ProductDto productDTO) {
        return null;
    }

    @Override
    public PageResponseDto<ProductDto> getProductsByFiler(ProductFilter filterDTO) {
      return null;
    }

    @Override
    public ProductDto findProductById(Long id) {
        return null;
    }

    @Override
    public ProductDto updateProductById(Long id, ProductDto productDTO) {
       return null;
    }

    @Override
    public void deleteProductById(Long id) {

    }
}
