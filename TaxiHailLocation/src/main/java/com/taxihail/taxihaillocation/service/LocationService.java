package com.taxihail.taxihaillocation.service;

import com.taxihail.taxihaillocation.dto.RideRequestDTo;
import com.taxihail.taxihaillocation.model.Location;
import com.taxihail.taxihaillocation.model.LocationDump;
import com.taxihail.taxihaillocation.repository.LocationDumpRepository;
import com.taxihail.taxihaillocation.repository.LocationRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class LocationService {
    private final LocationRepository locationRepository;
    private final LocationDumpRepository locationDumpRepository;

    public LocationService(LocationRepository locationRepository, LocationDumpRepository locationDumpRepository) {
        this.locationRepository = locationRepository;
        this.locationDumpRepository = locationDumpRepository;
    }

    @Transactional
    public void addLocation(Location location) {
        locationRepository.save(location);
        LocationDump locationDump = new LocationDump();
        BeanUtils.copyProperties(location, locationDump);
        locationDumpRepository.save(locationDump);
    }

    public List<UUID> getAvailableDrivers(RideRequestDTo rideRequestDTo) {

        String pickupParts = Arrays.toString(rideRequestDTo.getPickupLocation().split(","));
        String destinationParts = Arrays.toString(rideRequestDTo.getDestinationLocation().split(","));

        Pageable pageable = PageRequest.of(0, 5);
        Instant sixSecondsAgo = Instant.now().minusSeconds(6);
        Timestamp timestamp = Timestamp.from(sixSecondsAgo);
        List<UUID> driverIdList = locationRepository.getAvailableDrivers(rideRequestDTo.getVehicleType(), timestamp, pageable);

        return driverIdList;
    }
}
