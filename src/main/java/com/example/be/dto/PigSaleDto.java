package com.example.be.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PigSaleDto {
    private Long id;
    private LocalDate saleDate;
    private Integer quantity;
    private BigDecimal weight;
    private BigDecimal price;
    private BigDecimal total;
    private String customer;
    private String note;
}
