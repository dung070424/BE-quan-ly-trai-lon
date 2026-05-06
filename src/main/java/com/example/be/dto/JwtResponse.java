package com.example.be.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private Long id;
    private String username;
    private String role;
    private String name;
    private String image;
    private boolean mustChangePassword;

    public JwtResponse(String accessToken, Long id, String username, String role, String name, String image, boolean mustChangePassword) {
        this.token = accessToken;
        this.id = id;
        this.username = username;
        this.role = role;
        this.name = name;
        this.image = image;
        this.mustChangePassword = mustChangePassword;
    }
}
