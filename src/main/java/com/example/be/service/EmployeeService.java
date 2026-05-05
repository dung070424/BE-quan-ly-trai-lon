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

    @Autowired
    private com.example.be.repository.UserRepository userRepository;

    @Autowired
    private org.springframework.security.crypto.password.PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    public EmployeeDto createEmployee(EmployeeDto employeeDto) {
        Employee employee = convertToEntity(employeeDto);
        
        // Auto-generate employee code
        employee.setEmployeeCode(generateNextEmployeeCode());
        
        Employee savedEmployee = employeeRepository.save(employee);

        // Auto-create a user account for the new employee
        String generatedPassword = java.util.UUID.randomUUID().toString().substring(0, 8);
        com.example.be.entity.User newUser = userRepository.findByUsername(savedEmployee.getEmployeeCode())
                .orElse(new com.example.be.entity.User());
        newUser.setUsername(savedEmployee.getEmployeeCode());
        newUser.setPassword(passwordEncoder.encode(generatedPassword));
        newUser.setRole(com.example.be.entity.Role.NHANVIEN);
        userRepository.save(newUser);

        // Send credentials via email if email is provided
        if (savedEmployee.getEmail() != null && !savedEmployee.getEmail().trim().isEmpty()) {
            try {
                emailService.sendNewEmployeeCredentials(
                        savedEmployee.getEmail(),
                        savedEmployee.getName(),
                        newUser.getUsername(),
                        generatedPassword
                );
            } catch (Exception e) {
                System.err.println("Failed to send email to " + savedEmployee.getEmail() + ": " + e.getMessage());
            }
        }

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
        existingEmployee.setPhoneNumber(employeeDto.getPhoneNumber());
        existingEmployee.setEmail(employeeDto.getEmail());
        existingEmployee.setImage(employeeDto.getImage());
        
        Employee updatedEmployee = employeeRepository.save(existingEmployee);
        return convertToDto(updatedEmployee);
    }

    @org.springframework.transaction.annotation.Transactional
    public void deleteEmployee(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + id));
                
        // Delete associated user to prevent orphaned accounts
        userRepository.findByUsername(employee.getEmployeeCode())
                .ifPresent(user -> userRepository.delete(user));
                
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
                employee.getGender(),
                employee.getPhoneNumber(),
                employee.getEmail(),
                employee.getImage()
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
        employee.setPhoneNumber(employeeDto.getPhoneNumber());
        employee.setEmail(employeeDto.getEmail());
        employee.setImage(employeeDto.getImage());
        return employee;
    }
}
