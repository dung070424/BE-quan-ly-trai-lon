package com.example.be.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;

import java.time.LocalDate;

@Entity
@Table(name = "sows")
public class Sow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String tagNumber; // Mã lợn nái

    private String breed;     // Giống
    private LocalDate birthDate; // Ngày sinh
    private String origin;    // Nguồn gốc
    private String status;    // Trạng thái (Đang chửa, Trống, Đang nuôi con, ...)
    private String notes;     // Ghi chú

    public Sow() {
    }

    public Sow(Long id, String tagNumber, String breed, LocalDate birthDate, String origin, String status, String notes) {
        this.id = id;
        this.tagNumber = tagNumber;
        this.breed = breed;
        this.birthDate = birthDate;
        this.origin = origin;
        this.status = status;
        this.notes = notes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTagNumber() {
        return tagNumber;
    }

    public void setTagNumber(String tagNumber) {
        this.tagNumber = tagNumber;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
