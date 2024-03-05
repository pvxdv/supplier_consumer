package com.pvxdv.supplier.api;

import com.pvxdv.supplier.dto.CategoryDTO;
import com.pvxdv.supplier.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/categories")
@AllArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping()
    public ResponseEntity<CategoryDTO> createNewCategory (@RequestBody CategoryDTO categoryDTO){
        return new ResponseEntity<>(categoryService.createNewCategory(categoryDTO), HttpStatus.CREATED);
    }

//    @GetMapping()
//    public ResponseEntity<List<CategoryDTO>> getAllCategories(){
//        return new ResponseEntity<>(categoryService.findAllCategories(), HttpStatus.OK);
//    }

    @GetMapping()
    public ResponseEntity<List<CategoryDTO>> searchCategories(@RequestParam(value = "search") String search){
            return new ResponseEntity<>(categoryService.searchCategories(search), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable Long id){
        return new ResponseEntity<>(categoryService.findCategoryById(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryDTO> updateCategoryById(@PathVariable Long id, @RequestBody CategoryDTO categoryDTO){
        return new ResponseEntity<>(categoryService.updateCategoryById(id, categoryDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategoryById(@PathVariable Long id){
        categoryService.deleteCategoryById(id);
    }
}
