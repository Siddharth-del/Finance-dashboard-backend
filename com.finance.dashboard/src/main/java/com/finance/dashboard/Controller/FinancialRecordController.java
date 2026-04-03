package com.finance.dashboard.Controller;

import java.time.LocalDate;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.finance.dashboard.Service.FinancialService;
import com.finance.dashboard.config.AppCostant;
import com.finance.dashboard.model.RecordType;
import com.finance.dashboard.payload.FinancialRecordDTO;
import com.finance.dashboard.payload.FinancialRecordResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class FinancialRecordController {

    private final FinancialService financialService;

    @PostMapping("/admin/categories/{categoryId}/records")
    public ResponseEntity<FinancialRecordDTO> createRecord(@RequestBody @Valid FinancialRecordDTO financialRecordDTO,
            @PathVariable Long categoryId) {
        FinancialRecordDTO response = financialService.createRecord(financialRecordDTO, categoryId);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    
    
    @GetMapping("/analyst/records")
    public ResponseEntity<FinancialRecordResponse> getAllRecords(
            @RequestParam(name = "pageNumber", defaultValue = AppCostant.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = AppCostant.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(name = "pageNumber", defaultValue = AppCostant.SORT_RECORD_BY, required = false) String sortBy,
            @RequestParam(name = "pageNumber", defaultValue = AppCostant.SORT_DIR, required = false) String sortOrder) {
        FinancialRecordResponse response = financialService.getAllRecords(pageNumber, pageSize, sortBy, sortOrder);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/admin/records/{financialId}")
    public ResponseEntity<FinancialRecordDTO> deleteRecord(@PathVariable Long financialId) {
        FinancialRecordDTO deletedResponse = financialService.deleteRecords(financialId);
        return new ResponseEntity<>(deletedResponse, HttpStatus.OK);
    }

    @PutMapping("/admin/records/{financialId}")
    public ResponseEntity<FinancialRecordDTO> updateRecords(@RequestBody FinancialRecordDTO financialRecordDTO,
            @PathVariable Long financialId) {
        FinancialRecordDTO updatedResponse = financialService.updateRecords(financialRecordDTO, financialId);
        return new ResponseEntity<>(updatedResponse, HttpStatus.OK);
    }

    @GetMapping("/analyst/records/{financialId}")
    public ResponseEntity<FinancialRecordResponse> getRecordsById(
            @RequestParam(name = "pageNumber", defaultValue = AppCostant.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = AppCostant.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(name = "pageNumber", defaultValue = AppCostant.SORT_RECORD_BY, required = false) String sortBy,
            @RequestParam(name = "pageNumber", defaultValue = AppCostant.SORT_DIR, required = false) String sortOrder,
            @PathVariable Long financialId) {
        FinancialRecordResponse financialRecordResponse = financialService.getRecordsById(pageNumber, pageSize, sortBy,
                sortOrder, financialId);
        return new ResponseEntity<>(financialRecordResponse, HttpStatus.OK);
    }

    @GetMapping("/analyst/records/category/{categoryId}")
    public ResponseEntity<FinancialRecordResponse> getRecordsByCategory(
            @RequestParam(name = "pageNumber", defaultValue = AppCostant.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = AppCostant.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(name = "pageNumber", defaultValue = AppCostant.SORT_RECORD_BY, required = false) String sortBy,
            @RequestParam(name = "pageNumber", defaultValue = AppCostant.SORT_DIR, required = false) String sortOrder,
            @PathVariable Long categoryId) {
        FinancialRecordResponse financialRecordResponse = financialService.getRecordsByCategory(pageNumber, pageSize,
                sortBy, sortOrder, categoryId);
        return new ResponseEntity<>(financialRecordResponse, HttpStatus.OK);
    }

    @GetMapping("/analyst/records/date-range")
    public ResponseEntity<FinancialRecordResponse> getRecordsByDate(
            @RequestParam(name = "pageNumber", defaultValue = AppCostant.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = AppCostant.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(name = "pageNumber", defaultValue = AppCostant.SORT_RECORD_BY, required = false) String sortBy,
            @RequestParam(name = "pageNumber", defaultValue = AppCostant.SORT_DIR, required = false) String sortOrder,
            @RequestParam LocalDate startDate, @RequestParam LocalDate endDate) {
        FinancialRecordResponse recordResponse = financialService.getRecordsByDate(pageNumber, pageSize, sortBy,
                sortOrder, startDate, endDate);
        return new ResponseEntity<>(recordResponse, HttpStatus.OK);
    }

    @GetMapping("/analyst/records/type/{type}")
    public ResponseEntity<FinancialRecordResponse> getRecordByRecordType(
            @RequestParam(name = "pageNumber", defaultValue = AppCostant.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = AppCostant.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(name = "pageNumber", defaultValue = AppCostant.SORT_RECORD_BY, required = false) String sortBy,
            @RequestParam(name = "pageNumber", defaultValue = AppCostant.SORT_DIR, required = false) String sortOrder,
            @PathVariable RecordType type) {
        FinancialRecordResponse recordResponse = financialService.getRecordsByRecordType(pageNumber, pageSize, sortBy,
                sortOrder, type);
        return new ResponseEntity<>(recordResponse, HttpStatus.OK);

    }
      @GetMapping("/analyst/records/user/{userId}")
    public ResponseEntity<FinancialRecordResponse> getRecordsByUser(
            @RequestParam(name = "pageNumber", defaultValue = AppCostant.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = AppCostant.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(name = "pageNumber", defaultValue = AppCostant.SORT_RECORD_BY, required = false) String sortBy,
            @RequestParam(name = "pageNumber", defaultValue = AppCostant.SORT_DIR, required = false) String sortOrder,
            @PathVariable Long userId) {
        FinancialRecordResponse financialRecordResponse = financialService.getRecordsByUserId(pageNumber, pageSize,
                sortBy, sortOrder, userId);
        return new ResponseEntity<>(financialRecordResponse, HttpStatus.OK);
    }

}
