package com.pvxdv.supplier.service.impl;

import com.pvxdv.supplier.dto.ProductDto;
import com.pvxdv.supplier.exception.ResourceNotFoundException;
import com.pvxdv.supplier.mapper.impl.ProductDtoToProductMapper;
import com.pvxdv.supplier.mapper.impl.ProductToProductDtoMapper;
import com.pvxdv.supplier.model.Product;
import com.pvxdv.supplier.repository.CategoryRepository;
import com.pvxdv.supplier.repository.ProductRepository;
import com.pvxdv.supplier.service.ProductService;
import com.pvxdv.supplier.util.searchFilter.ProductFilter;
import com.pvxdv.supplier.util.searchFilter.QPredicates;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import static com.pvxdv.supplier.model.QCategory.category;
import static com.pvxdv.supplier.model.QProduct.product;

@Slf4j
@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductDtoToProductMapper productDtoToProductMapper;
    private final ProductToProductDtoMapper productToProductDtoMapper;
    private final String PRODUCT_NOT_FOUND = "Product with id=%d not found";
    private final String SAVE_PRODUCT = "Saving Product=%s";


    @Override
    public ProductDto createNewProduct(ProductDto productDTO) {
        Product productToSave = productDtoToProductMapper.map(productDTO);
        log.debug(SAVE_PRODUCT.formatted(productToSave));
        return productToProductDtoMapper.map(productRepository.save(productToSave));
    }

    @Override
    public Page<ProductDto> getProductsByFiler(ProductFilter filterDTO) {
        var predicate = QPredicates.builder();
        predicate.add(filterDTO.getName(), product.name::containsIgnoreCase);
        predicate.add(filterDTO.getDescription(), product.description::containsIgnoreCase);
        predicate.add(filterDTO.getPrice(), product.price::loe);
        if (categoryExist(filterDTO.getCategoryId())) {
            predicate.add(categoryRepository.findById(filterDTO.getCategoryId()).get(), category::eq);
        }

        return productRepository.findAll(predicate.build(), PageRequest.of(filterDTO.getOffset(), filterDTO.getLimit()))
                .map(productToProductDtoMapper::map);
    }

    @Override
    public ProductDto findProductById(Long productId) {
        return productToProductDtoMapper.map(productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException(PRODUCT_NOT_FOUND.formatted(productId))));

    }

    @Override
    public void updateProductById(Long productId, ProductDto productDTO) {
        if (productExist(productId)) {
            Product productToUpdate = productRepository.findById(productId).get();
            if (productDTO.categoryId() != null) {
                if (categoryExist(productDTO.categoryId())) {
                    productToUpdate.setCategory(categoryRepository.findById(productDTO.categoryId()).get());
                } else {
                    String categoryNotFound = "Category with id=%d not found";
                    throw new ResourceNotFoundException(categoryNotFound.formatted(productDTO.categoryId()));
                }
            }
            if (!productDTO.name().isBlank() && productDTO.name().length() > 4
                    && productDTO.name().length() < 30) {
                productToUpdate.setName(productDTO.name());
            }
            if (!productDTO.description().isBlank() && productDTO.description().length() > 4
                    && productDTO.description().length() < 255) {
                productToUpdate.setDescription(productDTO.description());
            }
            if (productDTO.price() != null && productDTO.price().doubleValue() > 0.001) {
                productToUpdate.setPrice(productDTO.price());
            }

            log.debug(SAVE_PRODUCT.formatted(productToUpdate));

            productRepository.save(productToUpdate);
        } else {
            throw new ResourceNotFoundException(PRODUCT_NOT_FOUND.formatted(productId));
        }
    }

    @Override
    public void deleteProductById(Long productId) {
        if (productExist(productId)) {
            productRepository.deleteById(productId);
            log.debug("Product with id=%d delete successfully".formatted(productId));
        } else {
            throw new ResourceNotFoundException(PRODUCT_NOT_FOUND.formatted(productId));
        }
    }

    private Boolean productExist(Long productId) {
        if (productId != null) {
            return productRepository.findById(productId).isPresent();
        } else {
            return false;
        }
    }

    private Boolean categoryExist(Long productId) {
        if (productId != null) {
            return categoryRepository.findById(productId).isPresent();
        }
        return false;
    }
}
