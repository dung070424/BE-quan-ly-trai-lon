package com.example.be.controller;

import com.example.be.dto.MedicineInventoryDto;
import com.example.be.service.MedicineInventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import com.example.be.dto.MedicineImportHistoryDto;

@RestController
@RequestMapping("/api/medicine-inventory")
@CrossOrigin("*") // Adjust for production
public class MedicineInventoryController {

    @Autowired
    private MedicineInventoryService inventoryService;

    @GetMapping
    public ResponseEntity<List<MedicineInventoryDto>> getAllInventory() {
        return ResponseEntity.ok(inventoryService.getAllInventory());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedicineInventoryDto> getInventoryById(@PathVariable Long id) {
        return ResponseEntity.ok(inventoryService.getInventoryById(id));
    }

    @GetMapping("/history")
    public ResponseEntity<List<MedicineImportHistoryDto>> getAllHistory() {
        return ResponseEntity.ok(inventoryService.getAllHistory());
    }

    @PostMapping("/import")
    public ResponseEntity<MedicineInventoryDto> importMedicine(@RequestBody MedicineInventoryDto dto) {
        return new ResponseEntity<>(inventoryService.importMedicine(dto), HttpStatus.CREATED);
    }

    @PostMapping("/{id}/export")
    public ResponseEntity<MedicineInventoryDto> exportMedicine(
            @PathVariable Long id, 
            @RequestParam Integer amount) {
        return ResponseEntity.ok(inventoryService.exportMedicine(id, amount));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInventory(@PathVariable Long id) {
        inventoryService.deleteInventory(id);
        return ResponseEntity.noContent().build();
    }
}
