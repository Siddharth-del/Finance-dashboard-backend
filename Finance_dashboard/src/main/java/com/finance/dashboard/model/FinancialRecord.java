package com.finance.dashboard.model;

import java.time.LocalDate;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "financial_Record")
@AllArgsConstructor
@NoArgsConstructor
public class FinancialRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long financialId;

    private double amount;

   
    @Enumerated(EnumType.STRING)
    private RecordType recordType;

    private String notes;

    private LocalDate date;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name = "category_id",nullable = false)
    private Category category;
     
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
