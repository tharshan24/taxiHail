package com.taxi.taxihailcore.repository;

import com.taxi.taxihailcore.model.Ride;
import com.taxi.taxihailcore.model.RideDriver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RideDriverRepository extends JpaRepository<RideDriver, UUID> {
    @Modifying
    @Query("UPDATE Ride r SET r.status = 0 WHERE r.rideId = :rideId")
    void softDelete(@Param("rideId") UUID rideId);

}
