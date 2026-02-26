package com.example.be.service;

import com.example.be.dto.MedicineImportHistoryDto;
import com.example.be.dto.MedicineInventoryDto;
import com.example.be.entity.Medicine;
import com.example.be.entity.MedicineImportHistory;
import com.example.be.entity.MedicineInventory;
import com.example.be.repository.MedicineImportHistoryRepository;
import com.example.be.repository.MedicineInventoryRepository;
import com.example.be.repository.MedicineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MedicineInventoryService {

    @Autowired
    private MedicineInventoryRepository inventoryRepository;

    @Autowired
    private MedicineRepository medicineRepository;

    @Autowired
    private MedicineImportHistoryRepository historyRepository;

    public List<MedicineInventoryDto> getAllInventory() {
        return inventoryRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public MedicineInventoryDto getInventoryById(Long id) {
        MedicineInventory inventory = inventoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Inventory not found with id: " + id));
        return convertToDto(inventory);
    }

    @Transactional
    public MedicineInventoryDto importMedicine(MedicineInventoryDto dto) {
        // If an inventory already exists for this medicine, update quantity. Otherwise, create new.
        Medicine medicine = medicineRepository.findById(dto.getMedicineId())
                .orElseThrow(() -> new RuntimeException("Medicine not found with id: " + dto.getMedicineId()));

        Optional<MedicineInventory> existingInventoryOpt = inventoryRepository.findByMedicineId(dto.getMedicineId());
        
        MedicineInventory inventory;
        if (existingInventoryOpt.isPresent()) {
            inventory = existingInventoryOpt.get();
            // Add quantity for import
            if (dto.getQuantity() != null) {
                inventory.setQuantity(inventory.getQuantity() + dto.getQuantity());
            }
            
            // Update other fields
            if (dto.getType() != null) inventory.setType(dto.getType());
            if (dto.getUnit() != null) inventory.setUnit(dto.getUnit());
            if (dto.getMinQuantity() != null) inventory.setMinQuantity(dto.getMinQuantity());
            if (dto.getMaxQuantity() != null) inventory.setMaxQuantity(dto.getMaxQuantity());
            if (dto.getUnitPrice() != null) inventory.setUnitPrice(dto.getUnitPrice());
            if (dto.getLocation() != null) inventory.setLocation(dto.getLocation());
        } else {
            inventory = new MedicineInventory();
            inventory.setMedicine(medicine);
            inventory.setType(dto.getType());
            inventory.setQuantity(dto.getQuantity() != null ? dto.getQuantity() : 0);
            inventory.setUnit(dto.getUnit());
            inventory.setMinQuantity(dto.getMinQuantity() != null ? dto.getMinQuantity() : 0);
            inventory.setMaxQuantity(dto.getMaxQuantity() != null ? dto.getMaxQuantity() : 9999);
            inventory.setUnitPrice(dto.getUnitPrice());
            inventory.setLocation(dto.getLocation());
        }

        inventory.setLastUpdated(LocalDate.now());
        
        MedicineInventory saved = inventoryRepository.save(inventory);

        // --- Save to History ---
        if (dto.getQuantity() != null && dto.getQuantity() > 0) {
            MedicineImportHistory history = new MedicineImportHistory();
            history.setMedicine(medicine);
            history.setImportDate(java.time.LocalDateTime.now());
            history.setQuantity(dto.getQuantity());
            history.setUnit(dto.getUnit() != null ? dto.getUnit() : inventory.getUnit());
            history.setUnitPrice(dto.getUnitPrice());
            
            Double totalPrice = null;
            if (dto.getUnitPrice() != null) {
                totalPrice = dto.getQuantity() * dto.getUnitPrice();
            }
            history.setTotalPrice(totalPrice);
            
            historyRepository.save(history);
        }

        return convertToDto(saved);
    }

    @Transactional
    public MedicineInventoryDto exportMedicine(Long inventoryId, Integer amount) {
        MedicineInventory inventory = inventoryRepository.findById(inventoryId)
                .orElseThrow(() -> new RuntimeException("Inventory not found with id: " + inventoryId));

        if (inventory.getQuantity() < amount) {
            throw new RuntimeException("Not enough medicine in inventory. Current: " 
                    + inventory.getQuantity() + ", requested: " + amount);
        }

        inventory.setQuantity(inventory.getQuantity() - amount);
        inventory.setLastUpdated(LocalDate.now());

        MedicineInventory saved = inventoryRepository.save(inventory);
        return convertToDto(saved);
    }

    public void deleteInventory(Long id) {
        MedicineInventory inventory = inventoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Inventory not found with id: " + id));
        inventoryRepository.delete(inventory);
    }

    public List<MedicineImportHistoryDto> getAllHistory() {
        return historyRepository.findAllByOrderByImportDateDesc().stream()
                .map(this::convertHistoryToDto)
                .collect(Collectors.toList());
    }

    private MedicineImportHistoryDto convertHistoryToDto(MedicineImportHistory h) {
        MedicineImportHistoryDto dto = new MedicineImportHistoryDto();
        dto.setId(h.getId());
        dto.setMedicineId(h.getMedicine().getId());
        dto.setMedicineName(h.getMedicine().getName());
        dto.setMedicineCode(h.getMedicine().getMedicineCode());
        dto.setImportDate(h.getImportDate());
        dto.setQuantity(h.getQuantity());
        dto.setUnit(h.getUnit());
        dto.setUnitPrice(h.getUnitPrice());
        dto.setTotalPrice(h.getTotalPrice());
        return dto;
    }

    private MedicineInventoryDto convertToDto(MedicineInventory inventory) {
        MedicineInventoryDto dto = new MedicineInventoryDto();
        dto.setId(inventory.getId());
        dto.setMedicineId(inventory.getMedicine().getId());
        dto.setMedicineName(inventory.getMedicine().getName());
        dto.setExpiryDate(inventory.getMedicine().getExpiryDate());
        
        dto.setType(inventory.getType());
        dto.setQuantity(inventory.getQuantity());
        dto.setUnit(inventory.getUnit());
        dto.setMinQuantity(inventory.getMinQuantity());
        dto.setMaxQuantity(inventory.getMaxQuantity());
        dto.setUnitPrice(inventory.getUnitPrice());
        dto.setLocation(inventory.getLocation());
        dto.setLastUpdated(inventory.getLastUpdated());
        return dto;
    }
}
