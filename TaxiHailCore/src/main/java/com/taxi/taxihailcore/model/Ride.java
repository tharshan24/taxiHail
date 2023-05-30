package com.taxi.taxihailcore.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;

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
    @JoinColumn(name = "driver", nullable = false)
    private User driver;

    @Column(name = "status", length = 1, nullable = false)
    private int status;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false, nullable = false)
    private Timestamp createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private Timestamp updatedAt;

    @OneToOne(mappedBy = "ride", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private Payment payment;

    public UUID getRideId() {
        return rideId;
    }

    public void setRideId(UUID rideId) {
        this.rideId = rideId;
    }

    public User getPassenger() {
        return passenger;
    }

    public void setPassenger(User passenger) {
        this.passenger = passenger;
    }

    public BigDecimal getPickupLocationLatitude() {
        return pickupLocationLatitude;
    }

    public void setPickupLocationLatitude(BigDecimal pickupLocationLatitude) {
        this.pickupLocationLatitude = pickupLocationLatitude;
    }

    public BigDecimal getPickupLocationLongitude() {
        return pickupLocationLongitude;
    }

    public void setPickupLocationLongitude(BigDecimal pickupLocationLongitude) {
        this.pickupLocationLongitude = pickupLocationLongitude;
    }

    public BigDecimal getDestinationLocationLatitude() {
        return destinationLocationLatitude;
    }

    public void setDestinationLocationLatitude(BigDecimal destinationLocationLatitude) {
        this.destinationLocationLatitude = destinationLocationLatitude;
    }

    public BigDecimal getDestinationLocationLongitude() {
        return destinationLocationLongitude;
    }

    public void setDestinationLocationLongitude(BigDecimal destinationLocationLongitude) {
        this.destinationLocationLongitude = destinationLocationLongitude;
    }

    public User getDriver() {
        return driver;
    }

    public void setDriver(User driver) {
        this.driver = driver;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }
}
