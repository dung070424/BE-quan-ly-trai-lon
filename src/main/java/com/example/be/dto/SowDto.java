package com.example.be.dto;

import java.time.LocalDate;

public class SowDto {
    private Long id;
    private String tagNumber;
    private String breed;
    private LocalDate birthDate;
    private String origin;
    private String status;
    private String notes;

    public SowDto() {
    }

    public SowDto(Long id, String tagNumber, String breed, LocalDate birthDate, String origin, String status, String notes) {
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
