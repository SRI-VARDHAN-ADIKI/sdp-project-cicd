package com.rayyan.backend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {
    @NotBlank(message = "Please enter your email.")
    private String email;
    @NotBlank(message = "Please enter your password.")
    private String password;
}
