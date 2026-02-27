package com.example.be.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DashboardDto {
    private Double totalRevenue;
    private Integer totalPigsSold;
    private Integer totalEmployees;
    private Double totalMedicineCost;
    private Double totalFeedCost;
    private Integer totalSows;
    private Integer totalBornAlive;
    
    // Grouped by Month string, e.g. "2023-01" -> value
    private Map<String, Double> salesOverTime; 
    private Map<String, Double> expensesOverTime;
    private Map<String, Integer> pigsSoldOverTime;
    private Map<String, Integer> pigsBornOverTime;
}
