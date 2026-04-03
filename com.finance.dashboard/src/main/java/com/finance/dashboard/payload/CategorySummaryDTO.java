package com.finance.dashboard.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CategorySummaryDTO {
     private String categoryName;
    private double total;
    public CategorySummaryDTO(String categoryName, double total) {
        this.categoryName = categoryName;
        this.total = total;
    }
}
