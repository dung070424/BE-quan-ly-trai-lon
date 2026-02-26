package com.example.be.controller;

import com.example.be.dto.MedicineDto;
import com.example.be.service.MedicineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/medicines")
@CrossOrigin("*") // Adjust for production later
public class MedicineController {

    @Autowired
    private MedicineService medicineService;

    @GetMapping
    public ResponseEntity<List<MedicineDto>> getAllMedicines() {
        return ResponseEntity.ok(medicineService.getAllMedicines());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedicineDto> getMedicineById(@PathVariable Long id) {
        return ResponseEntity.ok(medicineService.getMedicineById(id));
    }

    @PostMapping
    public ResponseEntity<MedicineDto> createMedicine(@RequestBody MedicineDto medicineDto) {
        return new ResponseEntity<>(medicineService.createMedicine(medicineDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MedicineDto> updateMedicine(@PathVariable Long id, @RequestBody MedicineDto medicineDto) {
        return ResponseEntity.ok(medicineService.updateMedicine(id, medicineDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMedicine(@PathVariable Long id) {
        medicineService.deleteMedicine(id);
        return ResponseEntity.noContent().build();
    }
}
