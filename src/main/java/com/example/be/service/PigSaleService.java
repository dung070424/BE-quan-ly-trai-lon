package com.example.be.service;

import com.example.be.dto.PigSaleDto;
import com.example.be.entity.PigSale;
import com.example.be.repository.PigSaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PigSaleService {

    @Autowired
    private PigSaleRepository pigSaleRepository;

    public List<PigSaleDto> getAllPigSales() {
        return pigSaleRepository.findAll().stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public PigSaleDto getPigSaleById(Long id) {
        PigSale pigSale = pigSaleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pig sale not found with id: " + id));
        return convertToDto(pigSale);
    }

    public PigSaleDto createPigSale(PigSaleDto pigSaleDto) {
        PigSale pigSale = convertToEntity(pigSaleDto);
        PigSale savedPigSale = pigSaleRepository.save(pigSale);
        return convertToDto(savedPigSale);
    }

    public PigSaleDto updatePigSale(Long id, PigSaleDto pigSaleDto) {
        PigSale existingPigSale = pigSaleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pig sale not found with id: " + id));
        
        existingPigSale.setSaleDate(pigSaleDto.getSaleDate());
        existingPigSale.setQuantity(pigSaleDto.getQuantity());
        existingPigSale.setWeight(pigSaleDto.getWeight());
        existingPigSale.setPrice(pigSaleDto.getPrice());
        existingPigSale.setTotal(pigSaleDto.getTotal());
        existingPigSale.setCustomer(pigSaleDto.getCustomer());
        
        PigSale updatedPigSale = pigSaleRepository.save(existingPigSale);
        return convertToDto(updatedPigSale);
    }

    public void deletePigSale(Long id) {
        PigSale pigSale = pigSaleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pig sale not found with id: " + id));
        pigSaleRepository.delete(pigSale);
    }

    private PigSaleDto convertToDto(PigSale pigSale) {
        return new PigSaleDto(
                pigSale.getId(),
                pigSale.getSaleDate(),
                pigSale.getQuantity(),
                pigSale.getWeight(),
                pigSale.getPrice(),
                pigSale.getTotal(),
                pigSale.getCustomer()
        );
    }

    private PigSale convertToEntity(PigSaleDto pigSaleDto) {
        PigSale pigSale = new PigSale();
        pigSale.setSaleDate(pigSaleDto.getSaleDate());
        pigSale.setQuantity(pigSaleDto.getQuantity());
        pigSale.setWeight(pigSaleDto.getWeight());
        pigSale.setPrice(pigSaleDto.getPrice());
        pigSale.setTotal(pigSaleDto.getTotal());
        pigSale.setCustomer(pigSaleDto.getCustomer());
        return pigSale;
    }
}
