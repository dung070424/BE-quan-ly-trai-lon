package com.example.be.repository;

import com.example.be.entity.Cam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CamRepository extends JpaRepository<Cam, Long> {
    
    // Find the last inserted cam to get the latest cam code
    Optional<Cam> findTopByOrderByIdDesc();
}
