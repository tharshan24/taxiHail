package com.taxi.taxihailcore.repository;

import com.taxi.taxihailcore.model.Ride;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RideRepository extends JpaRepository<Ride, UUID> {
    @Modifying
    @Query("UPDATE Ride r SET r.status = 0 WHERE r.rideId = :rideId")
    void softDelete(@Param("rideId") UUID rideId);

    @Query("SELECT rr FROM Ride rr WHERE rr.driver.userId = :driverId AND rr.status IN (1, 2, 3)")
    Optional<List<Ride>> findByDriverAndStatus(@Param("driverId") UUID driverId);

    @Query("SELECT rr FROM Ride rr WHERE rr.passenger.userId = :passengerId AND rr.status IN (1, 2, 3)")
    Optional<List<Ride>> findByPassengerAndStatus(@Param("passengerId") UUID passengerId);

    @Query("SELECT rr FROM Ride rr JOIN RideDriver rd WHERE rd.driver.userId = :driverId AND rd.status = 1 AND rr.status IN (1, 2, 3)")
    Optional<List<Ride>> findRideRequests(@Param("driverId") UUID driverId);

    @Query("SELECT rr FROM Ride rr WHERE rr.rideId = :rideId")
    Optional<Ride> findRideByRideId(UUID rideId);
}