package com.example.be.repository;

import com.example.be.entity.CamInventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CamInventoryRepository extends JpaRepository<CamInventory, Long> {
    Optional<CamInventory> findByCamId(Long camId);
}
