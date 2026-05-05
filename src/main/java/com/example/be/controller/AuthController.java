package com.example.be.controller;

import com.example.be.dto.JwtResponse;
import com.example.be.dto.LoginRequest;
import com.example.be.security.jwt.JwtUtils;
import com.example.be.security.services.UserDetailsImpl;
import com.example.be.repository.EmployeeRepository;
import com.example.be.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    EmployeeRepository employeeRepository;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        String role = roles.isEmpty() ? null : roles.get(0).replace("ROLE_", "");

        String name = "Admin";
        String image = null;

        if (role != null && role.equals("NHANVIEN")) {
            Employee employee = employeeRepository.findByEmployeeCode(userDetails.getUsername()).orElse(null);
            if (employee != null) {
                name = employee.getName();
                image = employee.getImage();
            }
        }

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                role,
                name,
                image));
    }
}
