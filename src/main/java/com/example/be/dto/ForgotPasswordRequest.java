package com.example.be.dto;

import lombok.Data;

@Data
public class ForgotPasswordRequest {
    private String username;
    private String email;
}
