package com.finance.dashboard.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.finance.dashboard.Service.CategoryService;
import com.finance.dashboard.config.AppCostant;
import com.finance.dashboard.payload.CategoryDTO;
import com.finance.dashboard.payload.CategoryResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping("/admin/categories")
    public ResponseEntity<CategoryDTO> createCategory(@RequestBody  @Valid CategoryDTO categoryDTO) {
        CategoryDTO response = categoryService.createcategory(categoryDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    
    @GetMapping("/analyst/categories")
    public ResponseEntity<CategoryResponse> getAllCategories(
            @RequestParam(name = "pageNumber", defaultValue = AppCostant.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(name = "pageNumber", defaultValue = AppCostant.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(name = "pageNumber", defaultValue = AppCostant.SORT_CATEGORIES_BY, required = false) String sortBy,
            @RequestParam(name = "pageNumber", defaultValue = AppCostant.SORT_DIR, required = false) String sortOrder) {
        CategoryResponse categoryResponse = categoryService.getAllCategory(pageNumber, pageSize, sortBy, sortOrder);
        return new ResponseEntity<>(categoryResponse, HttpStatus.OK);
    }
    @DeleteMapping("/admin/categories/{categoryId}")
    public ResponseEntity<CategoryDTO> deleteCategory(@PathVariable Long categoryId){
        CategoryDTO deleteCategory=categoryService.deleteCategory(categoryId);
        return new ResponseEntity<>(deleteCategory,HttpStatus.OK);
    }
    
    @PutMapping("/admin/categories/{categoryId}")
    public ResponseEntity<CategoryDTO> updateCategories(@RequestBody @Valid CategoryDTO categoryDTO,@PathVariable Long categoryId){
        CategoryDTO updateCategory=categoryService.updateCategory(categoryDTO, categoryId);
        return new ResponseEntity<>(updateCategory,HttpStatus.OK);
    }

}
