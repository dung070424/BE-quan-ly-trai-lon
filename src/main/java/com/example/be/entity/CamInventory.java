package com.example.be.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "cam_inventory")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CamInventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cam_id", nullable = false, unique = true)
    private Cam cam;

    @Column(name = "type")
    private String type; // Cám bột, Cám viên nén, etc.

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "unit")
    private String unit; // Bào, KG

    @Column(name = "min_quantity")
    private Integer minQuantity;

    @Column(name = "max_quantity")
    private Integer maxQuantity;

    @Column(name = "unit_price")
    private Double unitPrice;

    @Column(name = "location")
    private String location; // Kệ A1, Kho 1

    @Column(name = "last_updated")
    private LocalDate lastUpdated;
}
