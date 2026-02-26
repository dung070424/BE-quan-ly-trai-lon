package com.example.be.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "medicines")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Medicine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "medicine_code", nullable = false, unique = true)
    private String medicineCode;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "manufacturer")
    private String manufacturer;

    @Column(name = "expiry_date")
    private LocalDate expiryDate;

    @Column(name = "note", columnDefinition = "TEXT")
    private String note;
}
