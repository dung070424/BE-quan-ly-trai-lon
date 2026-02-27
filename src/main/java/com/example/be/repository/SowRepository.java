package com.example.be.repository;

import com.example.be.entity.Sow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SowRepository extends JpaRepository<Sow, Long> {
    Optional<Sow> findByTagNumber(String tagNumber);
}
