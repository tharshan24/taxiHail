package com.taxi.taxihailcore.repository;

import com.taxi.taxihailcore.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, UUID> {
    @Modifying
    @Query("UPDATE Vehicle v SET v.status = 0 WHERE v.vehicleId = :vehicleId")
    void softDelete(@Param("vehicleId") UUID vehicleId);
}
