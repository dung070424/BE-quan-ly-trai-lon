package com.example.be.controller;

import com.example.be.dto.FarrowingRecordDto;
import com.example.be.service.FarrowingRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/farrowing-records")
public class FarrowingRecordController {

    @Autowired
    private FarrowingRecordService farrowingRecordService;

    @GetMapping
    public ResponseEntity<List<FarrowingRecordDto>> getAllFarrowingRecords() {
        return ResponseEntity.ok(farrowingRecordService.getAllFarrowingRecords());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FarrowingRecordDto> getFarrowingRecordById(@PathVariable Long id) {
        return ResponseEntity.ok(farrowingRecordService.getFarrowingRecordById(id));
    }

    @PostMapping
    public ResponseEntity<FarrowingRecordDto> createFarrowingRecord(@RequestBody FarrowingRecordDto dto) {
        return new ResponseEntity<>(farrowingRecordService.createFarrowingRecord(dto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FarrowingRecordDto> updateFarrowingRecord(@PathVariable Long id, @RequestBody FarrowingRecordDto dto) {
        return ResponseEntity.ok(farrowingRecordService.updateFarrowingRecord(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFarrowingRecord(@PathVariable Long id) {
        farrowingRecordService.deleteFarrowingRecord(id);
        return ResponseEntity.noContent().build();
    }
}
