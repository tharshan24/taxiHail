package com.taxi.taxihailcore.repository;

import com.taxi.taxihailcore.model.VehicleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface VehicleTypeRepository extends JpaRepository<VehicleType, UUID> {
    @Modifying
    @Query("UPDATE VehicleType v SET v.status = 0 WHERE v.vehicleTypeId = :vehicleTypeId")
    void softDelete(@Param("vehicleTypeId") UUID vehicleTypeId);
}
