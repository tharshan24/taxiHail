package com.taxi.taxihailcore.dto;

import com.taxi.taxihailcore.model.Ride;
import com.taxi.taxihailcore.model.User;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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
