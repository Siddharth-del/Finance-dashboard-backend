package com.finance.dashboard.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryResponse {
    List<CategoryDTO> content;
    private Integer pageSize;
    private Integer pageNumber;
    private Integer totalPage;
    private Long totalElements;
    private boolean lastPage;

}
