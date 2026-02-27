package com.example.be.repository;

import com.example.be.entity.CamImportHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CamImportHistoryRepository extends JpaRepository<CamImportHistory, Long> {
    List<CamImportHistory> findAllByOrderByImportDateDesc();
}
