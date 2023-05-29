package com.taxi.taxihailcore.dto;

import com.taxi.taxihailcore.model.Role;
import com.taxi.taxihailcore.model.User;
import com.taxi.taxihailcore.model.VehicleType;
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
public class VehicleDTO {
    private UUID vehicleId;
    private String vehicleNumber;
    private VehicleType vehicleType;
    private User driver;
    private int status;
    private Instant createdAt;
    private Instant updatedAt;
}