package com.taxi.taxihailcore.controller;

import com.taxi.taxihailcore.dto.RideRequestDTo;
import com.taxi.taxihailcore.dto.RideViewDTO;
import com.taxi.taxihailcore.model.Ride;
import com.taxi.taxihailcore.model.Role;
import com.taxi.taxihailcore.model.User;
import com.taxi.taxihailcore.repository.RideRepository;
import com.taxi.taxihailcore.repository.UserRepository;
import com.taxi.taxihailcore.response.CommonResponse;
import com.taxi.taxihailcore.service.RideService;
import jakarta.servlet.http.HttpServletRequest;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/ride")
public class RideController {

    private final UserRepository userRepository;
    private final RideRepository rideRepository;

    private final RideService rideService;

    public RideController(UserRepository userRepository, RideRepository rideRepository, RideService rideService) {
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
        Optional<List<Ride>> optionalRides = rideRepository.findByPassengerAndStatus(userId);

        final String authHeader = httpServletRequest.getHeader("Authorization");

        if (!optionalRides.isEmpty()) {
            throw new RuntimeException("Already have an active ride !");
        }

        return ResponseEntity.ok(rideService.rideRequest(rideRequestDTo, optionalUser.get(), authHeader));
    }

    @GetMapping("urrent_rides")
    public ResponseEntity<List<RideViewDTO>> getCurrentRides(
            @NotNull HttpServletRequest httpServletRequest
    ) {
        UUID userId = UUID.fromString((String) httpServletRequest.getAttribute("userId"));
        Role role = (Role) httpServletRequest.getAttribute("ROLE");
        return ResponseEntity.ok(rideService.getCurrentRides(userId, role));
    }
}