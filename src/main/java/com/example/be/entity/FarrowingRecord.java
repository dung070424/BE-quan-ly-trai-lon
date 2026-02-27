package com.example.be.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDate;

@Entity
@Table(name = "farrowing_records")
public class FarrowingRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String sowTag;          // Mã lợn nái
    private LocalDate farrowingDate;// Ngày đẻ
    private Integer bornAlive;      // Số con đẻ ra sống
    private Integer stillborn;      // Số con chết lưu
    private Integer weanedPigs;     // Số lợn con cai sữa
    private LocalDate weaningDate;  // Ngày cai sữa
    private String healthStatus;    // Tình trạng sức khỏe
    private String notes;           // Ghi chú

    public FarrowingRecord() {
    }

    public FarrowingRecord(Long id, String sowTag, LocalDate farrowingDate, Integer bornAlive, Integer stillborn, Integer weanedPigs, LocalDate weaningDate, String healthStatus, String notes) {
        this.id = id;
        this.sowTag = sowTag;
        this.farrowingDate = farrowingDate;
        this.bornAlive = bornAlive;
        this.stillborn = stillborn;
        this.weanedPigs = weanedPigs;
        this.weaningDate = weaningDate;
        this.healthStatus = healthStatus;
        this.notes = notes;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSowTag() {
        return sowTag;
    }

    public void setSowTag(String sowTag) {
        this.sowTag = sowTag;
    }

    public LocalDate getFarrowingDate() {
        return farrowingDate;
    }

    public void setFarrowingDate(LocalDate farrowingDate) {
        this.farrowingDate = farrowingDate;
    }

    public Integer getBornAlive() {
        return bornAlive;
    }

    public void setBornAlive(Integer bornAlive) {
        this.bornAlive = bornAlive;
    }

    public Integer getStillborn() {
        return stillborn;
    }

    public void setStillborn(Integer stillborn) {
        this.stillborn = stillborn;
    }

    public Integer getWeanedPigs() {
        return weanedPigs;
    }

    public void setWeanedPigs(Integer weanedPigs) {
        this.weanedPigs = weanedPigs;
    }

    public LocalDate getWeaningDate() {
        return weaningDate;
    }

    public void setWeaningDate(LocalDate weaningDate) {
        this.weaningDate = weaningDate;
    }

    public String getHealthStatus() {
        return healthStatus;
    }

    public void setHealthStatus(String healthStatus) {
        this.healthStatus = healthStatus;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
