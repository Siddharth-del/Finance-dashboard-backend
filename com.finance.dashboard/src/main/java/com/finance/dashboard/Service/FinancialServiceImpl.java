package com.finance.dashboard.Service;

import java.time.LocalDate;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.finance.dashboard.exceptions.ResourceNotFoundException;
import com.finance.dashboard.model.Category;
import com.finance.dashboard.model.FinancialRecord;
import com.finance.dashboard.model.RecordType;
import com.finance.dashboard.model.User;
import com.finance.dashboard.payload.FinancialRecordDTO;
import com.finance.dashboard.payload.FinancialRecordResponse;
import com.finance.dashboard.repository.CategoryRepository;
import com.finance.dashboard.repository.FinancialRecordRepository;
import com.finance.dashboard.repository.UserRepository;
import com.finance.dashboard.utils.AuthUtil;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class FinancialServiceImpl implements FinancialService {

    private final FinancialRecordRepository financialRecordRepository;
    private final ModelMapper modelMapper;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final AuthUtil authUtil;

    @Override
    public FinancialRecordDTO createRecord(FinancialRecordDTO financialRecordDTO, Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category is not Found with id: " + categoryId));
        if (financialRecordDTO.getAmount() < 0) {
            throw new RuntimeException("Amount is less than zero !!");
        }
         User user=authUtil.loggedInUser();
        FinancialRecord financialRecord = new FinancialRecord();
        financialRecord.setAmount(financialRecordDTO.getAmount());
        financialRecord.setRecordType(RecordType.valueOf(financialRecordDTO.getType().toUpperCase()));
        financialRecord.setDate(financialRecordDTO.getDate());
        financialRecord.setNotes(financialRecordDTO.getNotes());
        financialRecord.setCategory(category);
        financialRecord.setUser(user);

        FinancialRecord savedRecord = financialRecordRepository.save(financialRecord);
        return modelMapper.map(savedRecord, FinancialRecordDTO.class);
    }

    @Override
    public FinancialRecordResponse getAllRecords(Integer pageNumber, Integer pageSize, String sortBy,
            String sortOrder) {
        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByAndOrder);
        Page<FinancialRecord> financialPage = financialRecordRepository.findAll(pageDetails);
        List<FinancialRecord> financialRecord = financialPage.getContent();
        List<FinancialRecordDTO> financialRecordDto = financialRecord.stream()
                .map(finance -> modelMapper.map(finance, FinancialRecordDTO.class))
                .toList();
        FinancialRecordResponse financialRecordResponse = new FinancialRecordResponse();
        financialRecordResponse.setContent(financialRecordDto);
        financialRecordResponse.setPageNumber(financialPage.getNumber());
        financialRecordResponse.setTotalElements(financialPage.getTotalElements());
        financialRecordResponse.setPageSize(financialPage.getSize());
        financialRecordResponse.setLastPage(financialPage.isLast());
        financialRecordResponse.setTotalPage(financialPage.getTotalPages());
        return financialRecordResponse;
    }

    @Override
    public FinancialRecordDTO deleteRecords(Long financialId) {
        FinancialRecord financialRecord = financialRecordRepository.findById(financialId)
                .orElseThrow(() -> new RuntimeException("Record not found with the recordId: " + financialId));
        financialRecordRepository.delete(financialRecord);
        return modelMapper.map(financialRecord, FinancialRecordDTO.class);
    }

    @Override
    public FinancialRecordDTO updateRecords(FinancialRecordDTO financialRecordDTO, Long financialId) {
        FinancialRecord savedRecord = financialRecordRepository.findById(financialId)
                .orElseThrow(() -> new RuntimeException(" Record is not Found with recordId: " + financialId));

        FinancialRecord financialRecord = modelMapper.map(financialRecordDTO, FinancialRecord.class);
        financialRecord.setFinancialId(financialId);

        savedRecord = financialRecordRepository.save(financialRecord);

        return modelMapper.map(savedRecord, FinancialRecordDTO.class);
    }

    @Override
    public FinancialRecordResponse getRecordsById(Integer pageNumber, Integer pageSize, String sortBy,
            String sortOrder, Long Id) {
        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByAndOrder);
        Page<FinancialRecord> financialPage = financialRecordRepository.findByfinancialId(pageDetails, Id);
        List<FinancialRecord> financialRecords = financialPage.getContent();
        List<FinancialRecordDTO> financialRecordDTOs = financialRecords.stream()
                .map(finance -> modelMapper.map(finance, FinancialRecordDTO.class)).toList();

        FinancialRecordResponse response = new FinancialRecordResponse();
        response.setContent(financialRecordDTOs);
        response.setPageNumber(financialPage.getNumber());
        response.setPageSize(financialPage.getSize());
        response.setTotalElements(financialPage.getTotalElements());
        response.setTotalPage(financialPage.getNumberOfElements());
        response.setLastPage(financialPage.isLast());

        return response;
    }

    @Override
    public FinancialRecordResponse getRecordsByCategory(Integer pageNumber, Integer pageSize, String sortBy,
            String sortOrder, Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "CategoryId", categoryId));

        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByAndOrder);
        Page<FinancialRecord> financialPage=financialRecordRepository.findByCategory_CategoryId(pageDetails,categoryId);

        List<FinancialRecord> financialRecords=financialPage.getContent();
        List<FinancialRecordDTO> financialRecordDTOs=financialRecords.stream().map(finance-> modelMapper.map(finance,FinancialRecordDTO.class)).toList();
       
         FinancialRecordResponse response = new FinancialRecordResponse();
        response.setContent(financialRecordDTOs);
        response.setPageNumber(financialPage.getNumber());
        response.setPageSize(financialPage.getSize());
        response.setTotalElements(financialPage.getTotalElements());
        response.setTotalPage(financialPage.getNumberOfElements());
        response.setLastPage(financialPage.isLast());
        

        return response;
    }

    @Override
    public FinancialRecordResponse getRecordsByDate(Integer pageNumber, Integer pageSize, String sortBy,
            String sortOrder, LocalDate startDate, LocalDate endDate) {
        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByAndOrder);
        Page<FinancialRecord> financialPage=financialRecordRepository.findByDateBetween(startDate,endDate,pageDetails);
        List<FinancialRecord> financialRecords=financialPage.getContent();
        List<FinancialRecordDTO> financialRecordDTOs=financialRecords.stream().map(finance-> modelMapper.map(finance,FinancialRecordDTO.class)).toList();
       
         FinancialRecordResponse response = new FinancialRecordResponse();
        response.setContent(financialRecordDTOs);
        response.setPageNumber(financialPage.getNumber());
        response.setPageSize(financialPage.getSize());
        response.setTotalElements(financialPage.getTotalElements());
        response.setTotalPage(financialPage.getNumberOfElements());
        response.setLastPage(financialPage.isLast());
        return response;
    }

    @Override
    public FinancialRecordResponse getRecordsByRecordType(Integer pageNumber,Integer pageSize,String sortBy,String sortOrder, RecordType type){
       Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByAndOrder);
        Page<FinancialRecord> financialPage=financialRecordRepository.findByRecordType(type,pageDetails);
         List<FinancialRecord> financialRecords=financialPage.getContent();
        List<FinancialRecordDTO> financialRecordDTOs=financialRecords.stream().map(finance-> modelMapper.map(finance,FinancialRecordDTO.class)).toList();
       
         FinancialRecordResponse response = new FinancialRecordResponse();
        response.setContent(financialRecordDTOs);
        response.setPageNumber(financialPage.getNumber());
        response.setPageSize(financialPage.getSize());
        response.setTotalElements(financialPage.getTotalElements());
        response.setTotalPage(financialPage.getNumberOfElements());
        response.setLastPage(financialPage.isLast());
        return response;
    }

    @Override
    public FinancialRecordResponse getRecordsByUserId(Integer pageNumber,Integer pageSize,String sortBy,String sortOrder,Long userId) {
      User user=userRepository.findById(userId)
                   .orElseThrow(()-> new ResourceNotFoundException("User","UserId",userId));

        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByAndOrder);
        Page<FinancialRecord>financialPage=financialRecordRepository.findByUserUserId(userId,pageDetails);

        List<FinancialRecord> financialRecords=financialPage.getContent();
        List<FinancialRecordDTO> financialRecordDTOs=financialRecords.stream().map(fr->modelMapper.map(fr,FinancialRecordDTO.class)).toList();
        
         FinancialRecordResponse response = new FinancialRecordResponse();
        response.setContent(financialRecordDTOs);
        response.setPageNumber(financialPage.getNumber());
        response.setPageSize(financialPage.getSize());
        response.setTotalElements(financialPage.getTotalElements());
        response.setTotalPage(financialPage.getNumberOfElements());
        response.setLastPage(financialPage.isLast());
        return response;
        
    }
}
