package com.example.be.repository;

import com.example.be.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    
    // Find the last inserted employee to get the latest employee code
    Optional<Employee> findTopByOrderByIdDesc();
}
