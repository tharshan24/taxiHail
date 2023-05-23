package com.taxi.taxihailcore.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequestDTO {
    private String username;
    private String email;
    private String password;
    private String passwordConfirm;
    private String firstName;
    private String lastName;
    private String mobile;
    private String role;
    private int status;
    private Instant lastLogin;
    private Instant createdAt;
    private Instant updatedAt;
}
