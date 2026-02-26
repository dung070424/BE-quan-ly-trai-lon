package com.example.be.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MedicineImportHistoryDto {
    private Long id;
    private Long medicineId;
    private String medicineName;
    private String medicineCode;
    
    private LocalDateTime importDate;
    private Integer quantity;
    private String unit;
    private Double unitPrice;
    private Double totalPrice;
}
