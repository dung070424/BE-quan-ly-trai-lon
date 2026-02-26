package com.example.be.controller;

import com.example.be.dto.PigSaleDto;
import com.example.be.service.PigSaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pig-sales")
public class PigSaleController {

    @Autowired
    private PigSaleService pigSaleService;

    @GetMapping
    public ResponseEntity<List<PigSaleDto>> getAllPigSales() {
        return ResponseEntity.ok(pigSaleService.getAllPigSales());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PigSaleDto> getPigSaleById(@PathVariable Long id) {
        return ResponseEntity.ok(pigSaleService.getPigSaleById(id));
    }

    @PostMapping
    public ResponseEntity<PigSaleDto> createPigSale(@RequestBody PigSaleDto pigSaleDto) {
        return new ResponseEntity<>(pigSaleService.createPigSale(pigSaleDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PigSaleDto> updatePigSale(@PathVariable Long id, @RequestBody PigSaleDto pigSaleDto) {
        return ResponseEntity.ok(pigSaleService.updatePigSale(id, pigSaleDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePigSale(@PathVariable Long id) {
        pigSaleService.deletePigSale(id);
        return ResponseEntity.noContent().build();
    }
}
