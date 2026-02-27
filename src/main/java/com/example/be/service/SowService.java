package com.example.be.service;

import com.example.be.dto.SowDto;
import com.example.be.entity.Sow;
import com.example.be.repository.SowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SowService {

    @Autowired
    private SowRepository sowRepository;

    public List<SowDto> getAllSows() {
        return sowRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public SowDto getSowById(Long id) {
        Sow sow = sowRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sow not found with id: " + id));
        return convertToDto(sow);
    }

    public SowDto createSow(SowDto dto) {
        // Optional: Check if tagNumber already exists
        if (sowRepository.findByTagNumber(dto.getTagNumber()).isPresent()) {
            throw new RuntimeException("Sow with tag number " + dto.getTagNumber() + " already exists!");
        }

        Sow sow = convertToEntity(dto);
        Sow savedSow = sowRepository.save(sow);
        return convertToDto(savedSow);
    }

    public SowDto updateSow(Long id, SowDto dto) {
        Sow existingSow = sowRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sow not found with id: " + id));

        // Check if updating tagNumber to one that already exists on another sow
        if (!existingSow.getTagNumber().equals(dto.getTagNumber()) && 
            sowRepository.findByTagNumber(dto.getTagNumber()).isPresent()) {
            throw new RuntimeException("Sow with tag number " + dto.getTagNumber() + " already exists!");
        }

        existingSow.setTagNumber(dto.getTagNumber());
        existingSow.setBreed(dto.getBreed());
        existingSow.setBirthDate(dto.getBirthDate());
        existingSow.setOrigin(dto.getOrigin());
        existingSow.setStatus(dto.getStatus());
        existingSow.setNotes(dto.getNotes());

        Sow updatedSow = sowRepository.save(existingSow);
        return convertToDto(updatedSow);
    }

    public void deleteSow(Long id) {
        Sow sow = sowRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sow not found with id: " + id));
        sowRepository.delete(sow);
    }

    private SowDto convertToDto(Sow sow) {
        return new SowDto(
                sow.getId(),
                sow.getTagNumber(),
                sow.getBreed(),
                sow.getBirthDate(),
                sow.getOrigin(),
                sow.getStatus(),
                sow.getNotes()
        );
    }

    private Sow convertToEntity(SowDto dto) {
        Sow sow = new Sow();
        sow.setId(dto.getId());
        sow.setTagNumber(dto.getTagNumber());
        sow.setBreed(dto.getBreed());
        sow.setBirthDate(dto.getBirthDate());
        sow.setOrigin(dto.getOrigin());
        sow.setStatus(dto.getStatus());
        sow.setNotes(dto.getNotes());
        return sow;
    }
}
