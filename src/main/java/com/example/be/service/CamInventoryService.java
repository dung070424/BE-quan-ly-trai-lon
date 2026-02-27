package com.example.be.service;

import com.example.be.dto.CamImportHistoryDto;
import com.example.be.dto.CamInventoryDto;
import com.example.be.entity.Cam;
import com.example.be.entity.CamImportHistory;
import com.example.be.entity.CamInventory;
import com.example.be.repository.CamImportHistoryRepository;
import com.example.be.repository.CamInventoryRepository;
import com.example.be.repository.CamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CamInventoryService {

    @Autowired
    private CamInventoryRepository inventoryRepository;

    @Autowired
    private CamRepository camRepository;

    @Autowired
    private CamImportHistoryRepository historyRepository;

    public List<CamInventoryDto> getAllInventory() {
        return inventoryRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public CamInventoryDto getInventoryById(Long id) {
        CamInventory inventory = inventoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Inventory not found with id: " + id));
        return convertToDto(inventory);
    }

    @Transactional
    public CamInventoryDto importCam(CamInventoryDto dto) {
        // If an inventory already exists for this cam, update quantity. Otherwise, create new.
        Cam cam = camRepository.findById(dto.getCamId())
                .orElseThrow(() -> new RuntimeException("Cam not found with id: " + dto.getCamId()));

        Optional<CamInventory> existingInventoryOpt = inventoryRepository.findByCamId(dto.getCamId());
        
        CamInventory inventory;
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
            inventory = new CamInventory();
            inventory.setCam(cam);
            inventory.setType(dto.getType());
            inventory.setQuantity(dto.getQuantity() != null ? dto.getQuantity() : 0);
            inventory.setUnit(dto.getUnit());
            inventory.setMinQuantity(dto.getMinQuantity() != null ? dto.getMinQuantity() : 0);
            inventory.setMaxQuantity(dto.getMaxQuantity() != null ? dto.getMaxQuantity() : 9999);
            inventory.setUnitPrice(dto.getUnitPrice());
            inventory.setLocation(dto.getLocation());
        }

        inventory.setLastUpdated(LocalDate.now());
        
        CamInventory saved = inventoryRepository.save(inventory);

        // --- Save to History ---
        if (dto.getQuantity() != null && dto.getQuantity() > 0) {
            CamImportHistory history = new CamImportHistory();
            history.setCam(cam);
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
    public CamInventoryDto exportCam(Long inventoryId, Integer amount) {
        CamInventory inventory = inventoryRepository.findById(inventoryId)
                .orElseThrow(() -> new RuntimeException("Inventory not found with id: " + inventoryId));

        if (inventory.getQuantity() < amount) {
            throw new RuntimeException("Not enough cam in inventory. Current: " 
                    + inventory.getQuantity() + ", requested: " + amount);
        }

        inventory.setQuantity(inventory.getQuantity() - amount);
        inventory.setLastUpdated(LocalDate.now());

        CamInventory saved = inventoryRepository.save(inventory);
        return convertToDto(saved);
    }

    public void deleteInventory(Long id) {
        CamInventory inventory = inventoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Inventory not found with id: " + id));
        inventoryRepository.delete(inventory);
    }

    public List<CamImportHistoryDto> getAllHistory() {
        return historyRepository.findAllByOrderByImportDateDesc().stream()
                .map(this::convertHistoryToDto)
                .collect(Collectors.toList());
    }

    private CamImportHistoryDto convertHistoryToDto(CamImportHistory h) {
        CamImportHistoryDto dto = new CamImportHistoryDto();
        dto.setId(h.getId());
        dto.setCamId(h.getCam().getId());
        dto.setCamName(h.getCam().getName());
        dto.setCamCode(h.getCam().getCamCode());
        dto.setImportDate(h.getImportDate());
        dto.setQuantity(h.getQuantity());
        dto.setUnit(h.getUnit());
        dto.setUnitPrice(h.getUnitPrice());
        dto.setTotalPrice(h.getTotalPrice());
        return dto;
    }

    private CamInventoryDto convertToDto(CamInventory inventory) {
        CamInventoryDto dto = new CamInventoryDto();
        dto.setId(inventory.getId());
        dto.setCamId(inventory.getCam().getId());
        dto.setCamName(inventory.getCam().getName());
        dto.setExpiryDate(inventory.getCam().getExpiryDate());
        
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
