package com.example.be.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "pig_sales")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PigSale {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sale_date", nullable = false)
    private LocalDate saleDate;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "weight")
    private BigDecimal weight;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "total")
    private BigDecimal total;

    @Column(name = "customer")
    private String customer;
}
