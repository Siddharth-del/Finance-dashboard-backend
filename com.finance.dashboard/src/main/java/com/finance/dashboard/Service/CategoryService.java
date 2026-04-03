package com.finance.dashboard.Service;

import com.finance.dashboard.payload.CategoryDTO;
import com.finance.dashboard.payload.CategoryResponse;

public interface CategoryService {
    CategoryDTO createcategory(CategoryDTO categoryDTO);

    CategoryResponse getAllCategory(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    CategoryDTO deleteCategory(Long categoryId);

    CategoryDTO updateCategory(CategoryDTO categoryDTO, Long categoryId);
}
