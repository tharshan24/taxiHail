package com.taxihail.taxihaillocation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LocationDTO {
    private UUID driver;
    private UUID vehicle;
    private UUID vehicleType;
    private BigDecimal locationLatitude;
    private BigDecimal locationLongitude;
    private int inRide;
    private UUID ride; // You can replace "Object" with the appropriate class if "ride" has a specific structure.
    private int status;
    private Timestamp createdAt;

    public void setCreatedAt(String createdAt) {
        this.createdAt = Timestamp.valueOf(createdAt);
    }
}