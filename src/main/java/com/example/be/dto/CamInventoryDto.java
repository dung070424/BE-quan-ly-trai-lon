package com.example.be.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CamInventoryDto {
    private Long id;
    private Long camId;
    
    // Flat fields fetched from Cam for easier UI display
    private String camName;
    private LocalDate expiryDate;
    
    // Inventory specific fields
    private String type;
    private Integer quantity;
    private String unit;
    private Integer minQuantity;
    private Integer maxQuantity;
    private Double unitPrice;
    private String location;
    private LocalDate lastUpdated;
}
