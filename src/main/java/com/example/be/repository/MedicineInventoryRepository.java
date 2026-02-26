package com.example.be.repository;

import com.example.be.entity.MedicineInventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MedicineInventoryRepository extends JpaRepository<MedicineInventory, Long> {
    Optional<MedicineInventory> findByMedicineId(Long medicineId);
}
