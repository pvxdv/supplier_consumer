package com.pvxdv.consumer.api;


import com.pvxdv.consumer.dto.CategoryDto;
import com.pvxdv.consumer.dto.CategoryListDto;
import com.pvxdv.consumer.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@Tag(name = "CategoryController", description = "CRUD operations with Category Entity")
@RestController
@RequestMapping("api/v1/categories")
@AllArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @Operation(
            summary = "Create new category",
            description = "Create new category"
    )
    @PostMapping()
    public ResponseEntity<CategoryDto> createNewCategory (@RequestBody CategoryDto categoryDTO){
        return categoryService.createNewCategory(categoryDTO);
    }

    @Operation(
            summary = "Get all categories",
            description = "Get all categories"
    )
    @GetMapping()
    public ResponseEntity<CategoryListDto> getAllCategories(){
        return categoryService.findAllCategories();
    }

    @Operation(
            summary = "Get current category",
            description = "Get current category by category_id"
    )
    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable Long id){
        return categoryService.findCategoryById(id);
    }

    @Operation(
            summary = "Update current category",
            description = "Update current category by category_id"
    )
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateCategoryById(@PathVariable Long id, @RequestBody CategoryDto categoryDTO){
        categoryService.updateCategoryById(id, categoryDTO);
    }

    @Operation(
            summary = "Delete current category",
            description = "Delete current category by category_id"
    )
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategoryById(@PathVariable Long id){
        categoryService.deleteCategoryById(id);
    }
}
