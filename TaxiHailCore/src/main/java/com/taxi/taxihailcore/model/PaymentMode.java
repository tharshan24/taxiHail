package com.taxi.taxihailcore.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "payment_modes")
public class PaymentMode {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "payment_mode_id", columnDefinition = "UUID default gen_random_uuid()")
    private UUID paymentModeId;

    @Column(name = "payment_mode", length = 32, unique = true, nullable = false)
    private String paymentMode;

    @Column(name = "payment_short", length = 8, unique = true, nullable = false)
    private String paymentShort;

    @Column(name = "description", length = 256, nullable = true)
    private String description;

    @Column(name = "status", length = 1, nullable = false)
    private int status;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false, nullable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @OneToMany(mappedBy = "paymentMode", cascade = CascadeType.ALL)
    private List<Payment> payments;
}

