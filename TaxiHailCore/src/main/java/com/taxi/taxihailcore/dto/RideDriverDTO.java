package com.taxi.taxihailcore.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RideDriverDTO {
    private UUID rideRequestId;
    private UUID ride;
    private UUID driver;
    private int status; // 1: pending, 2: accepted, 3: cancelled, 4: missed, 0: rejected
    private Timestamp createdAt;
    private Timestamp updatedAt;
}
