package com.taxi.taxihailcore.model;

import jakarta.persistence.*;
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
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ride_requests")
public class RideDriver {

    @Id
    @Column(name = "ride_request_id", columnDefinition = "UUID default gen_random_uuid()")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID rideRequestId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "ride", nullable = false)
    private Ride ride;

    @ManyToOne(optional = false)
    @JoinColumn(name = "driver", nullable = false)
    private User driver;

    @Column(name = "status", length = 1, nullable = false)
    private int status; // 1: pending, 2: accepted, 3: cancelled, 4: missed, 0: rejected

    @CreationTimestamp
    @Column(name = "created_at", updatable = false, nullable = false)
    private Timestamp createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private Timestamp updatedAt;

}