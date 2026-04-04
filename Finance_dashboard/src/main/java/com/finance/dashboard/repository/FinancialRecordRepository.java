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
import com.finance.dashboard.model.FinancialRecord;
import com.finance.dashboard.model.RecordType;

@Repository
public interface FinancialRecordRepository extends JpaRepository<FinancialRecord, Long> {

    Page<FinancialRecord> findByFinancialId(Long id, Pageable pageable);

    Page<FinancialRecord> findByCategory_CategoryId(Long categoryId, Pageable pageable);

    Page<FinancialRecord> findByDateBetween(LocalDate startDate, LocalDate endDate, Pageable pageable);

    Page<FinancialRecord> findByRecordType(RecordType type, Pageable pageable);

    Page<FinancialRecord> findByUser_UserId(Long userId, Pageable pageable);

    @Query("SELECT SUM(f.amount) FROM FinancialRecord f WHERE f.recordType = :type")
    Double getTotalByType(@Param("type") RecordType type);

    @Query("SELECT SUM(f.amount) FROM FinancialRecord f WHERE f.recordType = :type AND f.user.userId = :userId")
    Double getTotalByTypeAndUser(@Param("userId") Long userId, @Param("type") RecordType type);

    @Query("SELECT new com.finance.dashboard.dto.CategorySummaryDTO(c.categoryName, SUM(f.amount)) " +
            "FROM FinancialRecord f LEFT JOIN f.category c WHERE c IS NOT NULL GROUP BY c.categoryName")
    List<CategorySummaryDTO> getCategoryTotals();

    @Query("SELECT f FROM FinancialRecord f WHERE f.category IS NOT NULL ORDER BY f.date DESC LIMIT 5")
    List<FinancialRecord> findTop5ByOrderByDateDesc();

    @Query(value = """
            SELECT
                TO_CHAR(date, 'Mon') AS month,
                SUM(CASE WHEN record_type = 'INCOME' THEN amount ELSE 0 END) AS income,
                SUM(CASE WHEN record_type = 'EXPENSE' THEN amount ELSE 0 END) AS expense
            FROM financial_record
            GROUP BY TO_CHAR(date, 'Mon'), EXTRACT(MONTH FROM date)
            ORDER BY EXTRACT(MONTH FROM date)
            """, nativeQuery = true)
    List<Object[]> getMonthlyTrendRaw();
}