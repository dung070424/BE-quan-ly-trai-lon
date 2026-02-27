package com.example.be.controller;

import com.example.be.dto.CamDto;
import com.example.be.service.CamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cams")
@CrossOrigin("*") // Adjust for production later
public class CamController {

    @Autowired
    private CamService camService;

    @GetMapping
    public ResponseEntity<List<CamDto>> getAllCams() {
        return ResponseEntity.ok(camService.getAllCams());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CamDto> getCamById(@PathVariable Long id) {
        return ResponseEntity.ok(camService.getCamById(id));
    }

    @PostMapping
    public ResponseEntity<CamDto> createCam(@RequestBody CamDto camDto) {
        return new ResponseEntity<>(camService.createCam(camDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CamDto> updateCam(@PathVariable Long id, @RequestBody CamDto camDto) {
        return ResponseEntity.ok(camService.updateCam(id, camDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCam(@PathVariable Long id) {
        camService.deleteCam(id);
        return ResponseEntity.noContent().build();
    }
}
