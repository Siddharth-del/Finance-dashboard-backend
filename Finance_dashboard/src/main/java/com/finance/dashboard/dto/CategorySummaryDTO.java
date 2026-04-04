package com.finance.dashboard.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategorySummaryDTO {
     private String categoryName;
    private double total;

    // public CategorySummaryDTO(String categoryName, double total) {
    //     this.categoryName = categoryName;
    //     this.total = total;
    // }
}
