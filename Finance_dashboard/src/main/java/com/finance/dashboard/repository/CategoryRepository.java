package com.finance.dashboard.repository;

import com.finance.dashboard.model.Category;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Long> {

Category findByCategoryName(String categoryName);
    
}
