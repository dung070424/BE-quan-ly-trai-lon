package com.example.be.controller;

import com.example.be.dto.JwtResponse;
import com.example.be.dto.LoginRequest;
import com.example.be.security.jwt.JwtUtils;
import com.example.be.security.services.UserDetailsImpl;
import com.example.be.repository.EmployeeRepository;
import com.example.be.repository.UserRepository;
import com.example.be.service.EmailService;
import com.example.be.entity.Employee;
import com.example.be.entity.User;
import com.example.be.dto.ForgotPasswordRequest;
import com.example.be.dto.ResetPasswordRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    @Autowired
    UserRepository userRepository;

    @Autowired
    EmailService emailService;

    @Autowired
    PasswordEncoder passwordEncoder;

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

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody ForgotPasswordRequest request) {
        try {
            Employee employee = employeeRepository.findByEmployeeCode(request.getUsername()).orElse(null);
            if (employee == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Không tìm thấy nhân viên với tên đăng nhập này.");
            }
            if (employee.getEmail() == null || !employee.getEmail().equals(request.getEmail())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email không khớp với dữ liệu trên hệ thống.");
            }

            User user = userRepository.findByUsername(request.getUsername()).orElse(null);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Không tìm thấy tài khoản hệ thống cho nhân viên này.");
            }

            // Generate 6-digit OTP
            String otpCode = String.format("%06d", new java.util.Random().nextInt(999999));
            user.setResetCode(otpCode);
            userRepository.save(user);

            // Send email asynchronously to avoid blocking the UI
            new Thread(() -> {
                try {
                    emailService.sendPasswordResetOtp(
                            employee.getEmail(),
                            employee.getName(),
                            otpCode
                    );
                } catch (Exception ex) {
                    System.err.println("Failed to send OTP email: " + ex.getMessage());
                }
            }).start();

            return ResponseEntity.ok().body("{\"message\": \"Mã xác nhận 6 số đã được gửi vào email của bạn.\"}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Đã xảy ra lỗi: " + e.getMessage());
        }
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordRequest request) {
        try {
            User user = userRepository.findByUsername(request.getUsername()).orElse(null);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Không tìm thấy tài khoản hệ thống cho nhân viên này.");
            }

            if (user.getResetCode() == null || !user.getResetCode().equals(request.getResetCode())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Mã xác nhận không hợp lệ hoặc đã hết hạn.");
            }

            // Update new password
            user.setPassword(passwordEncoder.encode(request.getNewPassword()));
            // Clear reset code
            user.setResetCode(null);
            userRepository.save(user);

            return ResponseEntity.ok().body("{\"message\": \"Đặt lại mật khẩu thành công!\"}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Đã xảy ra lỗi: " + e.getMessage());
        }
    }
}
