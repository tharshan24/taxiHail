package com.taxihail.taxihaillocation.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "locations")
public class Location {

    @Id
    @Column(name = "driver")
    private UUID driver;

    @Column(name = "location_latitude", precision = 17, scale = 15, nullable = false)
    private BigDecimal locationLatitude;

    @Column(name = "location_longitude", precision = 18, scale = 15, nullable = false)
    private BigDecimal locationLongitude;

    @Column(name = "in_ride", length = 1, nullable = false)
    private int inRide; // 0: no, 1: yes

    @Column(name = "ride")
    private UUID ride;

    @Column(name = "vehicle")
    private UUID vehicle;

    @Column(name = "vehicleType")
    private UUID vehicleType;

    @Column(name = "status", length = 1, nullable = false)
    private int status;

    @Column(name = "created_at", updatable = false, nullable = false)
    private Timestamp createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private Timestamp updatedAt;
}
