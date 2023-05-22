package com.taxi.taxihailcore.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String username;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String mobile;
    private String role;
    private int status;
    private Instant lastLogin;
    private Instant createdAt;
    private Instant updatedAt;
}
