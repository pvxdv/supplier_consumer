package com.pvxdv.supplier.api;

import com.pvxdv.supplier.dto.ProductDTO;
import com.pvxdv.supplier.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/products")
@AllArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping()
    public ResponseEntity<ProductDTO> createNewProduct (@RequestBody ProductDTO productDTO){
        return new ResponseEntity<>(productService.createNewProduct(productDTO), HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<List<ProductDTO>> getAllProducts(){
        return new ResponseEntity<>(productService.findAllProducts(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id){
        return new ResponseEntity<>(productService.findProductById(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> updateProductById(@PathVariable Long id, @RequestBody ProductDTO productDTO){
        return new ResponseEntity<>(productService.updateProductById(id, productDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProductById(@PathVariable Long id){
        productService.deleteProductById(id);
    }
}
