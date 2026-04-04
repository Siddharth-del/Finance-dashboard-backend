package com.finance.dashboard.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.finance.dashboard.dto.CategorySummaryDTO;
import com.finance.dashboard.dto.DashboardDTO;
import com.finance.dashboard.dto.FinancialRecordDTO;
import com.finance.dashboard.dto.MonthlyTrendDTO;
import com.finance.dashboard.model.RecordType;
import com.finance.dashboard.repository.FinancialRecordRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class DashBoardServiceImpl implements DashBoardService {

    private final FinancialRecordRepository financialRecordRepository;
    private final ModelMapper modelMapper;

    @Override
    public DashboardDTO getDashboard() {
        Double income = financialRecordRepository.getTotalByType(RecordType.INCOME);
        Double expense = financialRecordRepository.getTotalByType(RecordType.EXPENSE);

        DashboardDTO dashboardDTO = new DashboardDTO();
        dashboardDTO.setTotalIncome(income != null ? income : 0);
        dashboardDTO.setTotalExpense(expense != null ? expense : 0);
        dashboardDTO.setNetBalance(dashboardDTO.getTotalIncome() - dashboardDTO.getTotalExpense());

        List<CategorySummaryDTO> categoryTotal = financialRecordRepository.getCategoryTotals();
        dashboardDTO.setCategoryTotals(categoryTotal);

        List<FinancialRecordDTO> transaction = financialRecordRepository.findTop5ByOrderByDateDesc()
                .stream()
                .map(fr -> {
                    FinancialRecordDTO dto = new FinancialRecordDTO();
                    dto.setFinancialId(fr.getFinancialId());
                    dto.setAmount(fr.getAmount());
                    dto.setType(fr.getRecordType().name() != null ? fr.getRecordType().name() : null);
                    dto.setNotes(fr.getNotes());
                    dto.setDate(fr.getDate());
                    dto.setCategoryName(
                            fr.getCategory().getCategoryName() != null ? fr.getCategory().getCategoryName() : null);
                    dto.setUserId(fr.getUser().getUserId() != null ? fr.getUser().getUserId() : null);
                    return dto;
                }).toList();

        dashboardDTO.setRecentTransactions(transaction);

        List<MonthlyTrendDTO> trends = financialRecordRepository.getMonthlyTrendRaw().stream().map(row -> new MonthlyTrendDTO((String) row[0],
                        row[1] != null ? ((Number) row[1]).doubleValue() : 0.0,
                        row[2] != null ? ((Number) row[2]).doubleValue() : 0.0)).toList();
        dashboardDTO.setMonthlyTrend(trends);

        return dashboardDTO;
    }

}