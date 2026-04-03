package com.finance.dashboard.Service;

import java.time.LocalDate;

import com.finance.dashboard.model.RecordType;
import com.finance.dashboard.payload.FinancialRecordDTO;
import com.finance.dashboard.payload.FinancialRecordResponse;

public interface FinancialService {
    FinancialRecordDTO createRecord(FinancialRecordDTO financialRecordDTO,Long categoryId);
    FinancialRecordResponse getAllRecords(Integer pageNumber,Integer pageSize,String sortBy,String sortOrder);
    FinancialRecordDTO deleteRecords(Long financialId);
    FinancialRecordDTO updateRecords(FinancialRecordDTO financialRecordDTO,Long financialId);
    FinancialRecordResponse getRecordsById(Integer pageNumber,Integer pageSize,String sortBy,String sortOrder,Long Id);
    FinancialRecordResponse getRecordsByCategory(Integer pageNumber,Integer pageSize,String sortBy,String sortOrder,Long categoryId);
    FinancialRecordResponse getRecordsByDate(Integer pageNumber,Integer pageSize,String sortBy,String sortOrder,LocalDate startDate,LocalDate endDate);
    FinancialRecordResponse getRecordsByRecordType(Integer pageNumber,Integer pageSize,String sortBy,String sortOrder, RecordType type);
    FinancialRecordResponse getRecordsByUserId(Integer pageNumber,Integer pageSize,String sortBy,String sortOrder,Long userId);
}
