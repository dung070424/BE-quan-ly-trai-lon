package com.example.be.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "medicine_inventory")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MedicineInventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "medicine_id", nullable = false, unique = true)
    private Medicine medicine;

    @Column(name = "type")
    private String type; // Kháng sinh, Vitamin, etc.

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "unit")
    private String unit; // viên, vỉ, hộp, chai

    @Column(name = "min_quantity")
    private Integer minQuantity;

    @Column(name = "max_quantity")
    private Integer maxQuantity;

    @Column(name = "unit_price")
    private Double unitPrice;

    @Column(name = "location")
    private String location; // Kệ A1, Kệ B2

    @Column(name = "last_updated")
    private LocalDate lastUpdated;
}
