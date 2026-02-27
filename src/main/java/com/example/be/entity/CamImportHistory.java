package com.example.be.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "cam_import_history")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CamImportHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cam_id", nullable = false)
    private Cam cam;

    @Column(name = "import_date")
    private LocalDateTime importDate;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "unit")
    private String unit;

    @Column(name = "unit_price")
    private Double unitPrice;

    @Column(name = "total_price")
    private Double totalPrice;
}
