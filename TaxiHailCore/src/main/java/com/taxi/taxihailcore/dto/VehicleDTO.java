package com.taxi.taxihailcore.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VehicleDTO {
    private UUID vehicleId;
    private String vehicleNo;
    private VehicleTypeDTO vehicleType;
    private UserDTO driver;
    private int status;
    private Instant createdAt;
    private Instant updatedAt;
}