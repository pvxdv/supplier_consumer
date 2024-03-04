package com.pvxdv.supplier.service.impl;

import com.pvxdv.supplier.dto.ProductDTO;
import com.pvxdv.supplier.model.Category;
import com.pvxdv.supplier.model.Product;
import com.pvxdv.supplier.repository.CategoryRepository;
import com.pvxdv.supplier.repository.ProductRepository;
import com.pvxdv.supplier.service.ProductService;
import com.pvxdv.supplier.util.ProductConverter;
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

    @Override
    public ProductDTO createNewProduct(ProductDTO productDTO) {
        if (categoryExist(productDTO.getCategoryId())) {
            Category productCategory = categoryRepository.findById(productDTO.getCategoryId()).get();
            Product productToSave = ProductConverter.productDTOToProduct(productDTO, productCategory);
            log.debug("Added new Product = %s".formatted(productToSave));

            return ProductConverter.productToProductDTO(productRepository.save(productToSave));
        }
        //todo exception
        return null;
    }

    @Override
    public List<ProductDTO> findAllProducts() {
        List<Product> products = productRepository.findAll();
        log.debug("Find all products =%s".formatted(products));
        List<ProductDTO> response = new ArrayList<>(products.size());

        for (Product product : products) {
            response.add(ProductConverter.productToProductDTO(product));
        }

        return response;
    }

    @Override
    public ProductDTO findProductById(Long id) {
        if (productExist(id)) {
            return ProductConverter.productToProductDTO(productRepository.findById(id).get());
        }
        //todo exception
        return null;
    }

    @Override
    public ProductDTO updateProductById(Long id, ProductDTO productDTO) {
        if (productExist(id)) {
            Product productToUpdate = productRepository.findById(id).get();
            if (productDTO.getCategoryId() != null) {
                if (categoryExist(productDTO.getCategoryId())) {
                    productToUpdate.setCategory(categoryRepository.findById(productDTO.getCategoryId()).get());
                } else {
                    //todo exception
                    return null;
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
            return ProductConverter.productToProductDTO(productRepository.save(productToUpdate));
        }
        //todo exception
        return null;
    }

    @Override
    public void deleteProductById(Long id) {
        if (productExist(id)) {
            productRepository.deleteById(id);
            log.debug("Product with id=%d delete successfully".formatted(id));
        }
        //todo exception
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
