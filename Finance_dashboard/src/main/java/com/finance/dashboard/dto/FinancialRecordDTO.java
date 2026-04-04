package com.finance.dashboard.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FinancialRecordDTO {
private Long financialId;

    private double amount;

    private String type;

    private String notes;

    private LocalDate date;

    private String categoryName; 
    private Long userId;

}
