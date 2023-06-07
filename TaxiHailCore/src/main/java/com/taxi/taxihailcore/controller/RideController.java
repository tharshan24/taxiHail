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

    @GetMapping("check_ride")
    public ResponseEntity<CommonResponse> checkRide(
            @NotNull HttpServletRequest httpServletRequest
    ) {
        UUID userId = UUID.fromString((String) httpServletRequest.getAttribute("userId"));
        Role role = Role.valueOf((String) httpServletRequest.getAttribute("ROLE"));
        List<RideViewDTO> optionalRides = rideService.getCurrentRides(userId, role);
        if (!optionalRides.isEmpty()) {
            return ResponseEntity.ok(CommonResponse.builder()
                            .message("Already have an active ride !")
                            .status(1)
                            .build());
        }
        else {
            return ResponseEntity.ok(CommonResponse.builder()
                    .message("Request for a ride !")
                    .status(0)
                    .build());
        }
    }

    @GetMapping("cancel_ride/{rideId}")
    public ResponseEntity<CommonResponse> cancelRide(
            @NotNull HttpServletRequest httpServletRequest,
            @PathVariable UUID rideId) {

        UUID userId = UUID.fromString((String) httpServletRequest.getAttribute("userId"));

        Optional<Ride> optionalRide = rideRepository.findById(rideId);

        if (optionalRide.isPresent()) {
            if (optionalRide.get().getPassenger().getUserId() != userId || optionalRide.get().getDriver().getUserId() != userId) {
                return ResponseEntity.ok(CommonResponse.builder()
                        .message("User Error")
                        .status(0)
                        .build());
            }
        }

        return ResponseEntity.ok(rideService.cancelRide(optionalRide.get()));
    }

    @GetMapping("change_ride/{rideId}/{status}")
    public ResponseEntity<CommonResponse> changeRide(
            @NotNull HttpServletRequest httpServletRequest,
            @PathVariable UUID rideId, @PathVariable int status) {

        UUID userId = UUID.fromString((String) httpServletRequest.getAttribute("userId"));

        Optional<Ride> optionalRide = rideRepository.findById(rideId);

        if (optionalRide.isPresent()) {
            if (optionalRide.get().getPassenger().getUserId() != userId || optionalRide.get().getDriver().getUserId() != userId) {
                return ResponseEntity.ok(CommonResponse.builder()
                        .message("User Error")
                        .status(0)
                        .build());
            }
        }

        return ResponseEntity.ok(rideService.changeRide(optionalRide.get(), status));
    }

    @GetMapping("accept_ride/{rideId}")
    public ResponseEntity<CommonResponse> acceptRide(
            @NotNull HttpServletRequest httpServletRequest,
            @PathVariable UUID rideId) {

        UUID userId = UUID.fromString((String) httpServletRequest.getAttribute("userId"));

        Optional<Ride> optionalRide = rideRepository.findById(rideId);

        return ResponseEntity.ok(rideService.acceptRide(optionalRide.get(), userId));
    }

    @GetMapping("accept_ride/{rideId}")
    public ResponseEntity<CommonResponse> rejectRide(
            @NotNull HttpServletRequest httpServletRequest,
            @PathVariable UUID rideId) {

        UUID userId = UUID.fromString((String) httpServletRequest.getAttribute("userId"));

        Optional<Ride> optionalRide = rideRepository.findById(rideId);

        return ResponseEntity.ok(rideService.acceptRide(optionalRide.get(), userId));
    }

    @GetMapping("current_rides")
    public ResponseEntity<List<RideViewDTO>> getCurrentRides(
            @NotNull HttpServletRequest httpServletRequest
    ) {
        UUID userId = UUID.fromString((String) httpServletRequest.getAttribute("userId"));
        Role role = Role.valueOf((String) httpServletRequest.getAttribute("ROLE"));
        return ResponseEntity.ok(rideService.getCurrentRides(userId, role));
    }

    @GetMapping("ride_requests")
    public ResponseEntity<List<RideViewDTO>> getRideRequests(
            @NotNull HttpServletRequest httpServletRequest
    ) {
        UUID userId = UUID.fromString((String) httpServletRequest.getAttribute("userId"));
        return ResponseEntity.ok(rideService.getRideRequests(userId));
    }
}