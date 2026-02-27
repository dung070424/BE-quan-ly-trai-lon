package com.example.be.service;

import com.example.be.dto.CamDto;
import com.example.be.entity.Cam;
import com.example.be.repository.CamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CamService {

    @Autowired
    private CamRepository camRepository;

    public List<CamDto> getAllCams() {
        return camRepository.findAll().stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public CamDto getCamById(Long id) {
        Cam cam = camRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cam not found with id: " + id));
        return convertToDto(cam);
    }

    public CamDto createCam(CamDto camDto) {
        Cam cam = convertToEntity(camDto);
        
        // Auto-generate cam code
        cam.setCamCode(generateNextCamCode());
        
        Cam savedCam = camRepository.save(cam);
        return convertToDto(savedCam);
    }

    public CamDto updateCam(Long id, CamDto camDto) {
        Cam existingCam = camRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cam not found with id: " + id));
        
        existingCam.setName(camDto.getName());
        existingCam.setManufacturer(camDto.getManufacturer());
        existingCam.setExpiryDate(camDto.getExpiryDate());
        existingCam.setNote(camDto.getNote());
        
        Cam updatedCam = camRepository.save(existingCam);
        return convertToDto(updatedCam);
    }

    public void deleteCam(Long id) {
        Cam cam = camRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cam not found with id: " + id));
        camRepository.delete(cam);
    }

    private String generateNextCamCode() {
        Optional<Cam> lastCam = camRepository.findTopByOrderByIdDesc();
        
        if (lastCam.isPresent() && lastCam.get().getCamCode() != null) {
            String lastCode = lastCam.get().getCamCode();
            try {
                String prefix = lastCode.replaceAll("[0-9]", "");
                String numberPart = lastCode.replaceAll("[^0-9]", "");
                
                if (!numberPart.isEmpty()) {
                    int nextNumber = Integer.parseInt(numberPart) + 1;
                    int digitCount = Math.max(3, numberPart.length());
                    return prefix + String.format("%0" + digitCount + "d", nextNumber);
                }
            } catch (Exception e) {
                // Ignore parsing errors, return default below
            }
        }
        
        return "C001";
    }

    private CamDto convertToDto(Cam cam) {
        return new CamDto(
                cam.getId(),
                cam.getCamCode(),
                cam.getName(),
                cam.getManufacturer(),
                cam.getExpiryDate(),
                cam.getNote()
        );
    }

    private Cam convertToEntity(CamDto camDto) {
        Cam cam = new Cam();
        cam.setCamCode(camDto.getCamCode());
        cam.setName(camDto.getName());
        cam.setManufacturer(camDto.getManufacturer());
        cam.setExpiryDate(camDto.getExpiryDate());
        cam.setNote(camDto.getNote());
        return cam;
    }
}
