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
public class VehicleTypeDTO {
    private UUID vehicleTypeId;
    private String vehicleType;
    private String vehicleShort;
    private String description;
    private int seatCount;
    private int status;
    private Instant createdAt;
    private Instant updatedAt;
}
