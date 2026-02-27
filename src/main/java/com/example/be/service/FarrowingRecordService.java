package com.example.be.service;

import com.example.be.dto.FarrowingRecordDto;
import com.example.be.entity.FarrowingRecord;
import com.example.be.repository.FarrowingRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FarrowingRecordService {

    @Autowired
    private FarrowingRecordRepository farrowingRecordRepository;

    public List<FarrowingRecordDto> getAllFarrowingRecords() {
        return farrowingRecordRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public FarrowingRecordDto getFarrowingRecordById(Long id) {
        FarrowingRecord record = farrowingRecordRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Farrowing record not found with id: " + id));
        return convertToDto(record);
    }

    public FarrowingRecordDto createFarrowingRecord(FarrowingRecordDto dto) {
        FarrowingRecord record = convertToEntity(dto);
        FarrowingRecord savedRecord = farrowingRecordRepository.save(record);
        return convertToDto(savedRecord);
    }

    public FarrowingRecordDto updateFarrowingRecord(Long id, FarrowingRecordDto dto) {
        FarrowingRecord existingRecord = farrowingRecordRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Farrowing record not found with id: " + id));

        existingRecord.setSowTag(dto.getSowTag());
        existingRecord.setFarrowingDate(dto.getFarrowingDate());
        existingRecord.setBornAlive(dto.getBornAlive());
        existingRecord.setStillborn(dto.getStillborn());
        existingRecord.setWeanedPigs(dto.getWeanedPigs());
        existingRecord.setWeaningDate(dto.getWeaningDate());
        existingRecord.setHealthStatus(dto.getHealthStatus());
        existingRecord.setNotes(dto.getNotes());

        FarrowingRecord updatedRecord = farrowingRecordRepository.save(existingRecord);
        return convertToDto(updatedRecord);
    }

    public void deleteFarrowingRecord(Long id) {
        FarrowingRecord record = farrowingRecordRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Farrowing record not found with id: " + id));
        farrowingRecordRepository.delete(record);
    }

    private FarrowingRecordDto convertToDto(FarrowingRecord record) {
        return new FarrowingRecordDto(
                record.getId(),
                record.getSowTag(),
                record.getFarrowingDate(),
                record.getBornAlive(),
                record.getStillborn(),
                record.getWeanedPigs(),
                record.getWeaningDate(),
                record.getHealthStatus(),
                record.getNotes()
        );
    }

    private FarrowingRecord convertToEntity(FarrowingRecordDto dto) {
        FarrowingRecord record = new FarrowingRecord();
        record.setId(dto.getId());
        record.setSowTag(dto.getSowTag());
        record.setFarrowingDate(dto.getFarrowingDate());
        record.setBornAlive(dto.getBornAlive());
        record.setStillborn(dto.getStillborn());
        record.setWeanedPigs(dto.getWeanedPigs());
        record.setWeaningDate(dto.getWeaningDate());
        record.setHealthStatus(dto.getHealthStatus());
        record.setNotes(dto.getNotes());
        return record;
    }
}
