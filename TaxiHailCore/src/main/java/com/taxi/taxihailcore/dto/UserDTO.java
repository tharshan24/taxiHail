package com.taxi.taxihailcore.dto;

import com.taxi.taxihailcore.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private UUID userId;
    private String userName;
    private String email;
    private String firstName;
    private String lastName;
    private String mobile;
    private Role role;
    private int status;
    private Timestamp lastLogin;
    private Instant createdAt;
    private Instant updatedAt;
}
