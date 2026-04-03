package com.finance.dashboard.payload;

import java.util.List;

import com.finance.dashboard.model.FinancialRecord;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DashboardDTO {
     private double totalIncome;
    private double totalExpense;
    private double netBalance;

    private List<CategorySummaryDTO> categoryTotals;
    private List<FinancialRecordDTO> recentTransactions;
    private List<MonthlyTrendDTO> monthlyTrend;
}
