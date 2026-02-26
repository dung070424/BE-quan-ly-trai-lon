package com.example.be.repository;

import com.example.be.entity.Medicine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MedicineRepository extends JpaRepository<Medicine, Long> {
    
    // Find the last inserted medicine to get the latest medicine code
    Optional<Medicine> findTopByOrderByIdDesc();
}
