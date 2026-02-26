package com.example.be.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MedicineDto {
    private Long id;
    private String medicineCode;
    private String name;
    private String manufacturer;
    private LocalDate expiryDate;
    private String note;
}
