package com.example.be.controller;

import com.example.be.dto.SowDto;
import com.example.be.service.SowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sows")
public class SowController {

    @Autowired
    private SowService sowService;

    @GetMapping
    public ResponseEntity<List<SowDto>> getAllSows() {
        return ResponseEntity.ok(sowService.getAllSows());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SowDto> getSowById(@PathVariable Long id) {
        return ResponseEntity.ok(sowService.getSowById(id));
    }

    @PostMapping
    public ResponseEntity<SowDto> createSow(@RequestBody SowDto dto) {
        return new ResponseEntity<>(sowService.createSow(dto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SowDto> updateSow(@PathVariable Long id, @RequestBody SowDto dto) {
        return ResponseEntity.ok(sowService.updateSow(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSow(@PathVariable Long id) {
        sowService.deleteSow(id);
        return ResponseEntity.noContent().build();
    }
}
