package com.taxihail.taxihaillocation.repository;

import com.taxihail.taxihaillocation.model.Location;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Repository
public interface LocationRepository extends JpaRepository<Location, UUID> {
    @Modifying
    @Query("UPDATE Location l SET l.status = 0 WHERE l.driver = :driverId")
    void softDelete(@Param("driverId") UUID driver);

    @Query("SELECT l.driver FROM Location l " +
            "WHERE l.vehicleType = :vehicleType " +
            "AND l.inRide = 0 " +
            "AND l.status = 1 " +
            "AND l.updatedAt >= :sixSecondsAgo")
    List<UUID> getAvailableDrivers(@Param("vehicleType") UUID vehicleType,
                                   @Param("sixSecondsAgo") Timestamp sixSecondsAgo,
                                   Pageable pageable);
}
