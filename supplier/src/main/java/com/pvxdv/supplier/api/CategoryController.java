package com.pvxdv.supplier.api;

import com.pvxdv.supplier.dto.CategoryDto;
import com.pvxdv.supplier.dto.CategoryListDto;
import com.pvxdv.supplier.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/categories")
@AllArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping()
    public ResponseEntity<CategoryDto> createNewCategory (@RequestBody CategoryDto categoryDTO){
        return new ResponseEntity<>(categoryService.createNewCategory(categoryDTO), HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<CategoryListDto> getAllCategories(){
        CategoryListDto response = new CategoryListDto(categoryService.findAllCategories());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable Long id){
        return new ResponseEntity<>(categoryService.findCategoryById(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateCategoryById(@PathVariable Long id, @RequestBody CategoryDto categoryDTO){
        categoryService.updateCategoryById(id, categoryDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategoryById(@PathVariable Long id){
        categoryService.deleteCategoryById(id);
    }
}
