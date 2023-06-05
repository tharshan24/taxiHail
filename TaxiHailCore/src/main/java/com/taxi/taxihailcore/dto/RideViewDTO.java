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
public class RideViewDTO {
    private UUID rideId;
    private String passenger;
    private BigDecimal pickupLocationLatitude;
    private BigDecimal pickupLocationLongitude;
    private BigDecimal destinationLocationLatitude;
    private BigDecimal destinationLocationLongitude;
    private String driver;
    private String vehicleType;
    private String vehicleNo;
    private BigDecimal amount;
    private int status;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}