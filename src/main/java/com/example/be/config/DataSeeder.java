package com.example.be.config;

import com.example.be.entity.Role;
import com.example.be.entity.User;
import com.example.be.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (!userRepository.existsByUsername("admin")) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRole(Role.ADMIN);
            userRepository.save(admin);
        }

        if (!userRepository.existsByUsername("nhanvien")) {
            User nhanvien = new User();
            nhanvien.setUsername("nhanvien");
            nhanvien.setPassword(passwordEncoder.encode("nhanvien123"));
            nhanvien.setRole(Role.NHANVIEN);
            userRepository.save(nhanvien);
        }
    }
}
