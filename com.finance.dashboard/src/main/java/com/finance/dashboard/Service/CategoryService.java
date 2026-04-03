package com.finance.dashboard.service;

import com.finance.dashboard.dto.CategoryDTO;
import com.finance.dashboard.dto.CategoryResponse;

public interface CategoryService {
    CategoryDTO createcategory(CategoryDTO categoryDTO);

    CategoryResponse getAllCategory(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    CategoryDTO deleteCategory(Long categoryId);

    CategoryDTO updateCategory(CategoryDTO categoryDTO, Long categoryId);
}
