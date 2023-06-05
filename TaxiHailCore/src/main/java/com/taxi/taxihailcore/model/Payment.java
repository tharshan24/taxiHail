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
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "payments")
public class Payment {

    @Id
    @Column(name = "payment_id", columnDefinition = "UUID default gen_random_uuid()")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID paymentId;

    @OneToOne
    @JoinColumn(name = "ride", referencedColumnName = "ride_id", nullable = false)
    private Ride ride;

    @Column(name = "payment_amount", precision = 19, scale = 2, nullable = false)
    private BigDecimal paymentAmount;

    @ManyToOne(optional = false)
    @JoinColumn(name = "payment_mode", columnDefinition = "UUID default 996d5cc3-0105-476d-b1f3-540906fe19a0")
    private PaymentMode paymentMode;

    @Column(name = "status", length = 1, nullable = false)
    private int status; // 0: cancelled, 1: pending, 2: paid

    @CreationTimestamp
    @Column(name = "created_at", updatable = false, nullable = false)
    private Timestamp createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private Timestamp updatedAt;

}
