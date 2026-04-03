
package com.finance.dashboard.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.finance.dashboard.dto.CategorySummaryDTO;
import com.finance.dashboard.dto.FinancialRecordDTO;
import com.finance.dashboard.dto.MonthlyTrendDTO;
import com.finance.dashboard.model.FinancialRecord;
import com.finance.dashboard.model.RecordType;
import com.finance.dashboard.model.User;

@Repository
public interface FinancialRecordRepository extends JpaRepository<FinancialRecord, Long> {

    Page<FinancialRecord> findByfinancialId(Pageable pageDetails, Long id);

    Page<FinancialRecord> findByCategory_CategoryId(Pageable pageDetails, Long categoryId);

    Page<FinancialRecord> findByDateBetween(LocalDate startDate, LocalDate endDate, Pageable pageDetails);

    Page<FinancialRecord> findByRecordType(RecordType type, Pageable pageDetails);

    @Query("SELECT SUM(f.amount) FROM FinancialRecord f WHERE f.recordType = :type")
    Double getTotalByType(@Param("type") RecordType type);

    
    @Query("SELECT new com.finance.dashboard.payload.CategorySummaryDTO(c.categoryName, SUM(f.amount)) " +
            "FROM FinancialRecord f LEFT JOIN f.category c WHERE c IS NOT NULL GROUP BY c.categoryName")
    List<CategorySummaryDTO> getCategoryTotals();

    @Query("SELECT f FROM FinancialRecord f WHERE f.category IS NOT NULL ORDER BY f.date DESC LIMIT 5")
    List<FinancialRecord> findTop5ByOrderByDateDesc();

    @Query("""
            SELECT new com.finance.dashboard.payload.MonthlyTrendDTO(
                TO_CHAR(f.date, 'Mon'),
                SUM(CASE WHEN f.recordType = 'INCOME' THEN f.amount ELSE 0 END),
                SUM(CASE WHEN f.recordType = 'EXPENSE' THEN f.amount ELSE 0 END)
            ) FROM FinancialRecord f GROUP BY TO_CHAR(f.date, 'Mon'), EXTRACT(MONTH FROM f.date) ORDER BY EXTRACT(MONTH FROM f.date)""")
    List<MonthlyTrendDTO> getMonthlyTrend();

    Page<FinancialRecord> findByUserUserId(Long user, Pageable pageDetails);
    
}