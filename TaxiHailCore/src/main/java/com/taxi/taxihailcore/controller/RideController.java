package com.taxi.taxihailcore.controller;

import com.taxi.taxihailcore.dto.RideRequestDTo;
import com.taxi.taxihailcore.exceptions.UserRegistrationException;
import com.taxi.taxihailcore.model.Ride;
import com.taxi.taxihailcore.model.User;
import com.taxi.taxihailcore.repository.RideRepository;
import com.taxi.taxihailcore.repository.UserRepository;
import com.taxi.taxihailcore.response.CommonResponse;
import com.taxi.taxihailcore.service.RideService;
import jakarta.servlet.http.HttpServletRequest;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/ride")
public class RideController {

    private final UserRepository userRepository;
    private final RideRepository rideRepository;

    private final RideService rideService;

    public RideController(UserRepository userRepository, RideRepository rideRepository, RideService rideService){
        this.userRepository = userRepository;
        this.rideRepository = rideRepository;
        this.rideService = rideService;
    }

    @PostMapping("request")
    public ResponseEntity<CommonResponse> rideRequest(
            @NotNull @RequestBody RideRequestDTo rideRequestDTo,
            @NotNull HttpServletRequest httpServletRequest
    ) {

        UUID userId = UUID.fromString((String) httpServletRequest.getAttribute("userId"));
        Optional<User> optionalUser = userRepository.findByUserId(userId);
        Optional<Ride> optionalRide = rideRepository.findByDriverAndStatus(userId);

        final String authHeader = httpServletRequest.getHeader("Authorization");

        if(optionalRide.isPresent()){
            throw new RuntimeException("Already have an active ride !");
        }

        return ResponseEntity.ok(rideService.rideRequest(rideRequestDTo, optionalUser.get(), authHeader));
    }
}