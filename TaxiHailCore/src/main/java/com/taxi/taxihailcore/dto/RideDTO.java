package com.taxi.taxihailcore.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RideDTO {
    private UUID rideId;
    private UUID passengerId;
    private BigDecimal pickupLocationLatitude;
    private BigDecimal pickupLocationLongitude;
    private BigDecimal destinationLocationLatitude;
    private BigDecimal destinationLocationLongitude;
    private UUID driverId;
    private int status;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}