package com.taxi.taxihailcore.repository;

import com.taxi.taxihailcore.model.Payment;
import com.taxi.taxihailcore.model.Ride;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, UUID> {
    @Modifying
    @Query("UPDATE Payment p SET p.status = 0 WHERE p.paymentId = :paymentId")
    void softDelete(@Param("paymentId") UUID paymentId);

    Payment findByRide(Ride ride);
}
