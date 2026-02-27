package com.example.be.controller;

import com.example.be.dto.CamInventoryDto;
import com.example.be.service.CamInventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import com.example.be.dto.CamImportHistoryDto;

@RestController
@RequestMapping("/api/cam-inventory")
@CrossOrigin("*") // Adjust for production
public class CamInventoryController {

    @Autowired
    private CamInventoryService inventoryService;

    @GetMapping
    public ResponseEntity<List<CamInventoryDto>> getAllInventory() {
        return ResponseEntity.ok(inventoryService.getAllInventory());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CamInventoryDto> getInventoryById(@PathVariable Long id) {
        return ResponseEntity.ok(inventoryService.getInventoryById(id));
    }

    @GetMapping("/history")
    public ResponseEntity<List<CamImportHistoryDto>> getAllHistory() {
        return ResponseEntity.ok(inventoryService.getAllHistory());
    }

    @PostMapping("/import")
    public ResponseEntity<CamInventoryDto> importCam(@RequestBody CamInventoryDto dto) {
        return new ResponseEntity<>(inventoryService.importCam(dto), HttpStatus.CREATED);
    }

    @PostMapping("/{id}/export")
    public ResponseEntity<CamInventoryDto> exportCam(
            @PathVariable Long id, 
            @RequestParam Integer amount) {
        return ResponseEntity.ok(inventoryService.exportCam(id, amount));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInventory(@PathVariable Long id) {
        inventoryService.deleteInventory(id);
        return ResponseEntity.noContent().build();
    }
}
