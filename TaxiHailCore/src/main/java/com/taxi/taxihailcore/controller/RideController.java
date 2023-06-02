package com.taxi.taxihailcore.controller;

import com.taxi.taxihailcore.model.Ride;
import com.taxi.taxihailcore.service.RideService;
import jakarta.servlet.http.HttpServletRequest;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ride")
public class RideController {

    private final RideService rideService;

    public RideController(RideService rideService){
        this.rideService = rideService;
    }

    @PostMapping("request")
    public ResponseEntity rideRequest(
            @NotNull @RequestBody Ride ride,
            @NotNull HttpServletRequest httpServletRequest
    ) {
        return null;
    }
}
