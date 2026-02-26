package com.example.be.service;

import com.example.be.dto.MedicineDto;
import com.example.be.entity.Medicine;
import com.example.be.repository.MedicineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MedicineService {

    @Autowired
    private MedicineRepository medicineRepository;

    public List<MedicineDto> getAllMedicines() {
        return medicineRepository.findAll().stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public MedicineDto getMedicineById(Long id) {
        Medicine medicine = medicineRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Medicine not found with id: " + id));
        return convertToDto(medicine);
    }

    public MedicineDto createMedicine(MedicineDto medicineDto) {
        Medicine medicine = convertToEntity(medicineDto);
        
        // Auto-generate medicine code
        medicine.setMedicineCode(generateNextMedicineCode());
        
        Medicine savedMedicine = medicineRepository.save(medicine);
        return convertToDto(savedMedicine);
    }

    public MedicineDto updateMedicine(Long id, MedicineDto medicineDto) {
        Medicine existingMedicine = medicineRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Medicine not found with id: " + id));
        
        existingMedicine.setName(medicineDto.getName());
        existingMedicine.setManufacturer(medicineDto.getManufacturer());
        existingMedicine.setExpiryDate(medicineDto.getExpiryDate());
        existingMedicine.setNote(medicineDto.getNote());
        
        Medicine updatedMedicine = medicineRepository.save(existingMedicine);
        return convertToDto(updatedMedicine);
    }

    public void deleteMedicine(Long id) {
        Medicine medicine = medicineRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Medicine not found with id: " + id));
        medicineRepository.delete(medicine);
    }

    private String generateNextMedicineCode() {
        Optional<Medicine> lastMedicine = medicineRepository.findTopByOrderByIdDesc();
        
        if (lastMedicine.isPresent() && lastMedicine.get().getMedicineCode() != null) {
            String lastCode = lastMedicine.get().getMedicineCode();
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
        
        return "TH001";
    }

    private MedicineDto convertToDto(Medicine medicine) {
        return new MedicineDto(
                medicine.getId(),
                medicine.getMedicineCode(),
                medicine.getName(),
                medicine.getManufacturer(),
                medicine.getExpiryDate(),
                medicine.getNote()
        );
    }

    private Medicine convertToEntity(MedicineDto medicineDto) {
        Medicine medicine = new Medicine();
        medicine.setMedicineCode(medicineDto.getMedicineCode());
        medicine.setName(medicineDto.getName());
        medicine.setManufacturer(medicineDto.getManufacturer());
        medicine.setExpiryDate(medicineDto.getExpiryDate());
        medicine.setNote(medicineDto.getNote());
        return medicine;
    }
}
