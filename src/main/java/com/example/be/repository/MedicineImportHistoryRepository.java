package com.example.be.repository;

import com.example.be.entity.MedicineImportHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicineImportHistoryRepository extends JpaRepository<MedicineImportHistory, Long> {
    List<MedicineImportHistory> findAllByOrderByImportDateDesc();
}
