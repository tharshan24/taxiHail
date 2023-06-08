package com.taxihail.taxihaillocation.model;

import jakarta.persistence.*;
import lombok.*;
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
@Table(name = "location_dumps")
public class LocationDump {

    @Id
    @Column(name = "dump_id", columnDefinition = "UUID default gen_random_uuid()")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID dumpId;

    @Column(name = "driver", nullable = false)
    private UUID driver;

    @Column(name = "vehicle", nullable = false)
    private UUID vehicle;

    @Column(name = "vehicleType")
    private UUID vehicleType;

    @Column(name = "location_latitude", precision = 17, scale = 15, nullable = false)
    private BigDecimal locationLatitude;

    @Column(name = "location_longitude", precision = 18, scale = 15, nullable = false)
    private BigDecimal locationLongitude;

    @Column(name = "in_ride", length = 1, nullable = false)
    private int inRide; // 0: no, 1: yes

    @Column(name = "ride")
    private UUID ride;

    @Column(name = "status", length = 1, nullable = false)
    private int status;

    @Column(name = "created_at", updatable = false, nullable = false)
    private Timestamp createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private Timestamp updatedAt;
}
