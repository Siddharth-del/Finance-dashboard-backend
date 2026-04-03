package com.finance.dashboard.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.finance.dashboard.dto.DashboardDTO;
import com.finance.dashboard.service.DashBoardService;

import jakarta.transaction.Transactional;import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Transactional
public class DashBoardController {
    private final DashBoardService dashBoardService;
    
    @GetMapping("/public/dashboard")
    public ResponseEntity<DashboardDTO> getDashBoard(){
        DashboardDTO dashboardDTO=dashBoardService.getDashboard();
        return new ResponseEntity<>(dashboardDTO,HttpStatus.OK);
    }
}
