package com.example.be.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CamDto {
    private Long id;
    private String camCode;
    private String name;
    private String manufacturer;
    private LocalDate expiryDate;
    private String note;
}
