package com.taxi.taxihailcore.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "vehicle_types")
public class VehicleType {
    @Id
    @Column(name = "vehicle_type_id", columnDefinition = "UUID default gen_random_uuid()")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID vehicleTypeId;

    @Column(name = "vehicle_type", length = 32, unique = true, nullable = false)
    private String vehicleType;

    @Column(name = "vehicle_short", length = 8, unique = true, nullable = false)
    private String vehicleShort;

    @Column(name = "seat_count", length = 2, nullable = false)
    private int seatCount;

    @Column(name = "description", length = 256)
    private String description;

    @Column(name = "status", length = 1, nullable = false)
    private int status;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false, nullable = false)
    private Timestamp createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private Timestamp updatedAt;

    @OneToMany(mappedBy = "vehicleType", cascade = CascadeType.ALL)
    private List<Vehicle> vehicles;

}
