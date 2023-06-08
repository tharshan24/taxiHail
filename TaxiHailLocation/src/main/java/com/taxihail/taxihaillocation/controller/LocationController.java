package com.taxihail.taxihaillocation.controller;

import com.taxihail.taxihaillocation.dto.RideRequestDTo;
import com.taxihail.taxihaillocation.service.LocationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/location")
public class LocationController {

    private final LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @PostMapping("get_available_drivers")
    public ResponseEntity<List<UUID>> getAvailableDrivers(
            @RequestBody RideRequestDTo request
    ) {
        return ResponseEntity.ok(locationService.getAvailableDrivers(request));
    }

}
