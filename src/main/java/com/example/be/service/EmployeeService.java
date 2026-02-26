package com.example.be.service;

import com.example.be.dto.EmployeeDto;
import com.example.be.entity.Employee;
import com.example.be.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public List<EmployeeDto> getAllEmployees() {
        return employeeRepository.findAll().stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public EmployeeDto getEmployeeById(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + id));
        return convertToDto(employee);
    }

    public EmployeeDto createEmployee(EmployeeDto employeeDto) {
        Employee employee = convertToEntity(employeeDto);
        
        // Auto-generate employee code
        employee.setEmployeeCode(generateNextEmployeeCode());
        
        Employee savedEmployee = employeeRepository.save(employee);
        return convertToDto(savedEmployee);
    }

    public EmployeeDto updateEmployee(Long id, EmployeeDto employeeDto) {
        Employee existingEmployee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + id));
        
        // We do not update employeeCode, only other fields
        existingEmployee.setName(employeeDto.getName());
        existingEmployee.setDateOfBirth(employeeDto.getDateOfBirth());
        existingEmployee.setIdentityCard(employeeDto.getIdentityCard());
        existingEmployee.setAddress(employeeDto.getAddress());
        existingEmployee.setGender(employeeDto.getGender());
        
        Employee updatedEmployee = employeeRepository.save(existingEmployee);
        return convertToDto(updatedEmployee);
    }

    public void deleteEmployee(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + id));
        employeeRepository.delete(employee);
    }

    private String generateNextEmployeeCode() {
        Optional<Employee> lastEmployee = employeeRepository.findTopByOrderByIdDesc();
        
        if (lastEmployee.isPresent() && lastEmployee.get().getEmployeeCode() != null) {
            String lastCode = lastEmployee.get().getEmployeeCode();
            try {
                // Assuming format is NVxxx like NV001
                String prefix = lastCode.replaceAll("[0-9]", "");
                String numberPart = lastCode.replaceAll("[^0-9]", "");
                
                if (!numberPart.isEmpty()) {
                    int nextNumber = Integer.parseInt(numberPart) + 1;
                    // Format back with same number of digits (e.g., %03d for 001)
                    int digitCount = Math.max(3, numberPart.length());
                    return prefix + String.format("%0" + digitCount + "d", nextNumber);
                }
            } catch (Exception e) {
                // If parsing fails, fallback
            }
        }
        
        // Default starting code
        return "NV001";
    }

    private EmployeeDto convertToDto(Employee employee) {
        return new EmployeeDto(
                employee.getId(),
                employee.getEmployeeCode(),
                employee.getName(),
                employee.getDateOfBirth(),
                employee.getIdentityCard(),
                employee.getAddress(),
                employee.getGender()
        );
    }

    private Employee convertToEntity(EmployeeDto employeeDto) {
        Employee employee = new Employee();
        employee.setEmployeeCode(employeeDto.getEmployeeCode());
        employee.setName(employeeDto.getName());
        employee.setDateOfBirth(employeeDto.getDateOfBirth());
        employee.setIdentityCard(employeeDto.getIdentityCard());
        employee.setAddress(employeeDto.getAddress());
        employee.setGender(employeeDto.getGender());
        return employee;
    }
}
