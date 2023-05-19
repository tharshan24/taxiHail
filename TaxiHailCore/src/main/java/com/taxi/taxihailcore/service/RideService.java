package com.taxi.taxihailcore.service;

import com.taxi.taxihailcore.repository.RideRepository;
import org.springframework.stereotype.Service;

@Service
public class RideService {
    private final RideRepository rideRepository;

    public RideService(RideRepository rideRepository){
        this.rideRepository = rideRepository;
    }
}
