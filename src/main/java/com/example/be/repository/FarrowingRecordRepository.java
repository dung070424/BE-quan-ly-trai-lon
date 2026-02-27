package com.example.be.repository;

import com.example.be.entity.FarrowingRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FarrowingRecordRepository extends JpaRepository<FarrowingRecord, Long> {
}
