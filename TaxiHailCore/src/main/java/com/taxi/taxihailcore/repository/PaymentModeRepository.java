package com.taxi.taxihailcore.repository;

import com.taxi.taxihailcore.model.PaymentMode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PaymentModeRepository extends JpaRepository<PaymentMode, UUID> {
    @Modifying
    @Query("UPDATE PaymentMode p SET p.status = 0 WHERE p.paymentModeId = :paymentModeId")
    void softDelete(@Param("paymentModeId") UUID paymentModeId);
}
