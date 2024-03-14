package com.pvxdv.supplier.api;

import com.pvxdv.supplier.dto.CategoryDto;
import com.pvxdv.supplier.dto.CategoryListDto;
import com.pvxdv.supplier.service.CategoryService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
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
    public ResponseEntity<CategoryDto> createNewCategory (@Valid @RequestBody CategoryDto categoryDTO){
        return new ResponseEntity<>(categoryService.createNewCategory(categoryDTO), HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<CategoryListDto> getAllCategories(){
        CategoryListDto response = new CategoryListDto(categoryService.findAllCategories());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable ("id") @Min(0) Long id){
        return new ResponseEntity<>(categoryService.findCategoryById(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateCategoryById(@PathVariable("id") @Min(0) Long id,
                                   @Valid @RequestBody CategoryDto categoryDTO){
        categoryService.updateCategoryById(id, categoryDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategoryById(@PathVariable ("id") @Min(0) Long id){
        categoryService.deleteCategoryById(id);
    }
}
