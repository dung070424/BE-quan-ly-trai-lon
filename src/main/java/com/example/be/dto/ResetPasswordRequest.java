package com.example.be.dto;

import lombok.Data;

@Data
public class ResetPasswordRequest {
    private String username;
    private String resetCode;
    private String newPassword;
}
