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
@Table(name = "vehicles")
public class Vehicle {

    @Id
    @Column(name = "vehicle_id", columnDefinition = "UUID default gen_random_uuid()")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID vehicleId;

    @Column(name = "vehicle_no", length = 11, unique = true, nullable = false)
    private String vehicleNo;

    @ManyToOne(optional = false)
    @JoinColumn(name = "vehicle_type", nullable = false)
    private VehicleType vehicleType;

    @OneToOne
    @JoinColumn(name = "driver", referencedColumnName = "user_id")
    private User driver;

    @Column(name = "status", length = 1, nullable = false)
    private int status;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false, nullable = false)
    private Timestamp createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private Timestamp updatedAt;

}
