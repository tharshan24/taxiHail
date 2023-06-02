package com.taxi.taxihailcore.controller;

import com.taxi.taxihailcore.service.RideService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ride")
public class RideController {

    private final RideService rideService;

    public RideController(RideService rideService){
        this.rideService = rideService;
    }
}
