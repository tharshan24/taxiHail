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

    @OneToMany(mappedBy = "paymentMode", cascade = CascadeType.ALL)
    private List<Payment> payments;

}

