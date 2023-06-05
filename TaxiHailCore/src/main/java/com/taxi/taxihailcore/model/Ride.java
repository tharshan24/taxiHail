package com.taxi.taxihailcore.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "rides")
public class Ride {

    @Id
    @Column(name = "ride_id", columnDefinition = "UUID default gen_random_uuid()")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID rideId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "passenger", nullable = false)
    private User passenger;

    @Column(name = "pickup_location_latitude", precision = 17, scale = 15, nullable = false)
    private BigDecimal pickupLocationLatitude;

    @Column(name = "pickup_location_longitude", precision = 18, scale = 15, nullable = false)
    private BigDecimal pickupLocationLongitude;

    @Column(name = "destination_location_latitude", precision = 17, scale = 15, nullable = false)
    private BigDecimal destinationLocationLatitude;

    @Column(name = "destination_location_longitude", precision = 18, scale = 15, nullable = false)
    private BigDecimal destinationLocationLongitude;

    @ManyToOne(optional = false)
    @JoinColumn(name = "driver")
    private User driver;

    @Column(name = "status", length = 1, nullable = false)
    private int status; // 0: cancelled, 1: pending, 2: driver_connected, 3: in_ride, 4: completed

    @CreationTimestamp
    @Column(name = "created_at", updatable = false, nullable = false)
    private Timestamp createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private Timestamp updatedAt;

    @OneToOne(mappedBy = "ride", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private Payment payment;

    @OneToMany(mappedBy = "ride", cascade = CascadeType.ALL)
    private List<RideDriver> rideDrivers;

}