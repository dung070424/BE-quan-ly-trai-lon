package com.example.be.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CamImportHistoryDto {
    private Long id;
    private Long camId;
    private String camName;
    private String camCode;
    
    private LocalDateTime importDate;
    private Integer quantity;
    private String unit;
    private Double unitPrice;
    private Double totalPrice;
}
