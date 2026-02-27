package com.example.be.service;

import com.example.be.dto.DashboardDto;
import com.example.be.entity.CamImportHistory;
import com.example.be.entity.MedicineImportHistory;
import com.example.be.entity.PigSale;
import com.example.be.repository.CamImportHistoryRepository;
import com.example.be.repository.EmployeeRepository;
import com.example.be.repository.MedicineImportHistoryRepository;
import com.example.be.repository.PigSaleRepository;
import com.example.be.repository.SowRepository;
import com.example.be.repository.FarrowingRecordRepository;
import com.example.be.entity.FarrowingRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Service
public class DashboardService {

    @Autowired
    private PigSaleRepository pigSaleRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private MedicineImportHistoryRepository medicineImportRepository;

    @Autowired
    private CamImportHistoryRepository camImportRepository;

    @Autowired
    private SowRepository sowRepository;

    @Autowired
    private FarrowingRecordRepository farrowingRecordRepository;

    public DashboardDto getDashboardSummary() {
        DashboardDto dto = new DashboardDto();

        // 1. Employees
        dto.setTotalEmployees((int) employeeRepository.count());

        // 1.5 Sows & Farrowing
        dto.setTotalSows((int) sowRepository.count());
        List<FarrowingRecord> farrowingRecords = farrowingRecordRepository.findAll();
        int totalBornAlive = 0;
        Map<String, Integer> pigsBornByMonth = new TreeMap<>();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM");

        for (FarrowingRecord fr : farrowingRecords) {
            int bornAlive = (fr.getBornAlive() != null) ? fr.getBornAlive() : 0;
            totalBornAlive += bornAlive;

            if (fr.getFarrowingDate() != null) {
                String monthKey = fr.getFarrowingDate().format(dateFormatter);
                int currentQty = pigsBornByMonth.getOrDefault(monthKey, 0);
                pigsBornByMonth.put(monthKey, currentQty + bornAlive);
            }
        }
        dto.setTotalBornAlive(totalBornAlive);
        dto.setPigsBornOverTime(pigsBornByMonth);

        // 2. Pig Sales (Revenue)
        List<PigSale> sales = pigSaleRepository.findAll();
        double totalRevenue = 0;
        int totalPigs = 0;
        Map<String, Double> salesByMonth = new TreeMap<>(); // TreeMap keeps it sorted
        Map<String, Integer> pigsSoldByMonth = new TreeMap<>();

        // Formatter already defined above

        for (PigSale sale : sales) {
            double saleTotal = (sale.getTotal() != null ? sale.getTotal().doubleValue() : 0.0);
            int qty = (sale.getQuantity() != null ? sale.getQuantity() : 0);
            totalRevenue += saleTotal;
            totalPigs += qty;
            
            if (sale.getSaleDate() != null) {
                String monthKey = sale.getSaleDate().format(dateFormatter);
                double currentVal = salesByMonth.getOrDefault(monthKey, 0.0);
                salesByMonth.put(monthKey, currentVal + saleTotal);
                
                int currentQty = pigsSoldByMonth.getOrDefault(monthKey, 0);
                pigsSoldByMonth.put(monthKey, currentQty + qty);
            }
        }
        dto.setTotalRevenue(totalRevenue);
        dto.setTotalPigsSold(totalPigs);
        dto.setSalesOverTime(salesByMonth);
        dto.setPigsSoldOverTime(pigsSoldByMonth);

        // 3. Expenses (Medicine + Cam)
        List<MedicineImportHistory> medicineImports = medicineImportRepository.findAll();
        List<CamImportHistory> camImports = camImportRepository.findAll();

        double totalMedicine = 0;
        double totalCam = 0;
        Map<String, Double> expensesByMonth = new TreeMap<>();

        for (MedicineImportHistory m : medicineImports) {
            totalMedicine += (m.getTotalPrice() != null ? m.getTotalPrice() : 0);
            if (m.getImportDate() != null) {
                String monthKey = m.getImportDate().format(dateFormatter);
                expensesByMonth.put(monthKey, expensesByMonth.getOrDefault(monthKey, 0.0) + (m.getTotalPrice() != null ? m.getTotalPrice() : 0));
            }
        }

        for (CamImportHistory c : camImports) {
            totalCam += (c.getTotalPrice() != null ? c.getTotalPrice() : 0);
             if (c.getImportDate() != null) {
                String monthKey = c.getImportDate().format(dateFormatter);
                expensesByMonth.put(monthKey, expensesByMonth.getOrDefault(monthKey, 0.0) + (c.getTotalPrice() != null ? c.getTotalPrice() : 0));
            }
        }

        dto.setTotalMedicineCost(totalMedicine);
        dto.setTotalFeedCost(totalCam);
        dto.setExpensesOverTime(expensesByMonth);

        return dto;
    }
}
