package com.example.be.repository;

import com.example.be.entity.PigSale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PigSaleRepository extends JpaRepository<PigSale, Long> {
}
