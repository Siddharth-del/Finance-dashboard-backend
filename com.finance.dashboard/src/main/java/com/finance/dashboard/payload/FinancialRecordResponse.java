package com.finance.dashboard.payload;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FinancialRecordResponse {
    List<FinancialRecordDTO> content;
    private Integer pageSize;
    private Integer pageNumber;
    private Integer totalPage;
    private Long totalElements;
    private boolean lastPage;

}
