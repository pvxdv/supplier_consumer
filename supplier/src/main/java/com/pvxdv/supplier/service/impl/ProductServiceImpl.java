package com.pvxdv.supplier.service.impl;

import com.pvxdv.supplier.dto.ProductDTO;
import com.pvxdv.supplier.exception.ResourceNotFoundException;
import com.pvxdv.supplier.model.Category;
import com.pvxdv.supplier.model.Product;
import com.pvxdv.supplier.repository.CategoryRepository;
import com.pvxdv.supplier.repository.ProductRepository;
import com.pvxdv.supplier.service.ProductService;
import com.pvxdv.supplier.util.mapper.ProductMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final String productNotFound = "Product with id=%d not found";
    private final String categoryNotFound = "Category with id=%d not found";

    @Override
    public ProductDTO createNewProduct(ProductDTO productDTO) {
        if (categoryExist(productDTO.getCategoryId())) {
            Category productCategory = categoryRepository.findById(productDTO.getCategoryId()).get();
            Product productToSave = ProductMapper.productDTOToProduct(productDTO, productCategory);
            log.debug("Added new Product = %s".formatted(productToSave));

            return ProductMapper.productToProductDTO(productRepository.save(productToSave));
        }
        throw new ResourceNotFoundException(categoryNotFound.formatted(productDTO.getCategoryId()));
    }

    @Override
    public List<ProductDTO> findAllProducts() {
        List<Product> products = productRepository.findAll();
        log.debug("Find all products =%s".formatted(products));
        List<ProductDTO> response = new ArrayList<>(products.size());

        for (Product product : products) {
            response.add(ProductMapper.productToProductDTO(product));
        }

        return response;
    }

    @Override
    public ProductDTO findProductById(Long id) {
        if (productExist(id)) {
            return ProductMapper.productToProductDTO(productRepository.findById(id).get());
        }
        throw new ResourceNotFoundException(productNotFound.formatted(id));
    }

    @Override
    public ProductDTO updateProductById(Long id, ProductDTO productDTO) {
        if (productExist(id)) {
            Product productToUpdate = productRepository.findById(id).get();
            if (productDTO.getCategoryId() != null) {
                if (categoryExist(productDTO.getCategoryId())) {
                    productToUpdate.setCategory(categoryRepository.findById(productDTO.getCategoryId()).get());
                } else {
                    throw new ResourceNotFoundException(categoryNotFound.formatted(productDTO.getCategoryId()));
                }
            }
            if (productDTO.getName() != null) {
                productToUpdate.setName(productDTO.getName());
            }
            if (productDTO.getDescription() != null) {
                productToUpdate.setDescription(productDTO.getDescription());
            }
            if (productDTO.getPrice() != null) {
                productToUpdate.setPrice(productDTO.getPrice());
            }
            log.debug("Product with id=%d update successfully =%s".formatted(id, productToUpdate));
            return ProductMapper.productToProductDTO(productRepository.save(productToUpdate));
        }
        throw new ResourceNotFoundException(productNotFound.formatted(id));
    }

    @Override
    public void deleteProductById(Long id) {
        if (productExist(id)) {
            productRepository.deleteById(id);
            log.debug("Product with id=%d delete successfully".formatted(id));
        }
        throw new ResourceNotFoundException(productNotFound.formatted(id));
    }

    private Boolean productExist(Long id) {
        Boolean productExist = productRepository.findById(id).isPresent();
        if (productExist) {
            log.debug("Find Product by id=%d - success".formatted(id));
        } else {
            log.debug("Find Product by id=%d - fail".formatted(id));
        }
        return productExist;
    }

    private Boolean categoryExist(Long id) {
        Boolean categoryExist = categoryRepository.findById(id).isPresent();
        if (categoryExist) {
            log.debug("Find Category by id=%d - success".formatted(id));
        } else {
            log.debug("Find Category by id=%d - fail".formatted(id));
        }
        return categoryExist;
    }
}
