package com.example.be.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDto {
    private Long id;
    private String employeeCode;
    private String name;
    private LocalDate dateOfBirth;
    private String identityCard;
    private String address;
    private String gender;
}
