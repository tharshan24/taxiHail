package com.taxi.taxihailcore.service;

import com.taxi.taxihailcore.dto.RideRequestDTo;
import com.taxi.taxihailcore.dto.RideViewDTO;
import com.taxi.taxihailcore.model.*;
import com.taxi.taxihailcore.repository.*;
import com.taxi.taxihailcore.response.CommonResponse;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.*;

@Service
public class RideService {
    private final RideRepository rideRepository;
    private final UserRepository userRepository;
    private final PaymentRepository paymentRepository;
    private final PaymentModeRepository paymentModeRepository;
    private final RideDriverRepository rideDriverRepository;

    public RideService(
            RideRepository rideRepository, UserRepository userRepository,
            PaymentRepository paymentRepository, PaymentModeRepository paymentModeRepository, RideDriverRepository rideDriverRepository
    ) {
        this.rideRepository = rideRepository;
        this.userRepository = userRepository;
        this.paymentRepository = paymentRepository;
        this.paymentModeRepository = paymentModeRepository;
        this.rideDriverRepository = rideDriverRepository;
    }

    @Transactional
    public CommonResponse rideRequest(RideRequestDTo rideRequestDTo, User passenger, String authHeader) {

        String[] pickupParts = rideRequestDTo.getPickupLocation().split(",");
        String[] destinationParts = rideRequestDTo.getDestinationLocation().split(",");

        try {
            //make REST request to location server
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
//            headers.set("Authorization", authHeader);

            HttpEntity<RideRequestDTo> requestEntity = new HttpEntity<>(rideRequestDTo, headers);

            RestTemplate restTemplate = new RestTemplate();
            String url = "http://localhost:8081/location/get_available_drivers";

            // Define the type reference for a list of UUIDs
            ParameterizedTypeReference<List<UUID>> responseType = new ParameterizedTypeReference<List<UUID>>() {
            };
            // Make the REST request
            ResponseEntity<List<UUID>> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, responseType);

            // Process the response
            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                List<UUID> driverIdList = responseEntity.getBody();

                if (driverIdList.isEmpty()) {
                    return CommonResponse.builder()
                            .status(0)
                            .message("No drivers available !")
                            .build();
                }

                var ride = Ride.builder()
                        .pickupLocationLatitude(new BigDecimal(pickupParts[0]))
                        .pickupLocationLongitude(new BigDecimal(pickupParts[1]))
                        .destinationLocationLatitude(new BigDecimal(destinationParts[0]))
                        .destinationLocationLongitude(new BigDecimal(destinationParts[1]))
                        .passenger(passenger)
                        .status(1)
                        .build();

                Ride savedRide = rideRepository.save(ride);

                Optional<PaymentMode> optionalPaymentMode = paymentModeRepository.findPaymentModeByPaymentModeId(UUID.fromString("996d5cc3-0105-476d-b1f3-540906fe19a0"));

                var payment = Payment.builder()
                        .ride(savedRide)
                        .paymentAmount(BigDecimal.valueOf(Math.floor(Math.random() * (2000 - 200 + 1)) + 2000))
                        .paymentMode(optionalPaymentMode.get())
                        .status(1)
                        .build();

                Payment payment1 = paymentRepository.save(payment);

                List<RideDriver> rideDriverList = new ArrayList<>();

                for (UUID driverId : driverIdList) {
                    RideDriver rideDriver = new RideDriver();
                    rideDriver.setRide(savedRide);

                    Optional<User> optionalUser = userRepository.findByUserId(driverId);
                    User driver = optionalUser.get();
                    rideDriver.setDriver(driver);

                    rideDriver.setStatus(1);
                    rideDriverList.add(rideDriver);
                }

                rideDriverRepository.saveAll(rideDriverList);

            } else {
                return CommonResponse.builder()
                        .status(0)
                        .message("Request failed with status code: " + responseEntity.getStatusCode())
                        .build();
            }

            return CommonResponse.builder()
                    .status(200)
                    .message("Ride request successful. Waiting for drivers.")
                    .build();

        } catch (Exception e) {
            return CommonResponse.builder()
                    .status(0)
                    .message("Ride request Failed: " + e)
                    .build();
        }
    }

    public List<RideViewDTO> getCurrentRides(UUID userId, Role role) {
        List<RideViewDTO> rideViewDTOList = new ArrayList<>();
        List<Ride> rideList = Collections.emptyList();

        if (role == Role.PASSENGER) {
            Optional<List<Ride>> passengerRides = rideRepository.findByPassengerAndStatus(userId);
            if (passengerRides.isPresent()) {
                rideList = passengerRides.get();
            }
        } else if (role == Role.DRIVER) {
            Optional<List<Ride>> driverRides = rideRepository.findByDriverAndStatus(userId);
            if (driverRides.isPresent()) {
                rideList = driverRides.get();
            }
        } else {
            throw new RuntimeException("User Role error !");
        }

        for (Ride ride : rideList) {
            RideViewDTO rideViewDTO = new RideViewDTO();

            rideViewDTO.setRideId(ride.getRideId());
            rideViewDTO.setPassenger(ride.getPassenger().getFirstName());
            rideViewDTO.setPickupLocationLatitude(ride.getPickupLocationLatitude());
            rideViewDTO.setPickupLocationLongitude(ride.getPickupLocationLongitude());
            rideViewDTO.setDestinationLocationLatitude(ride.getDestinationLocationLatitude());
            rideViewDTO.setDestinationLocationLongitude(ride.getDestinationLocationLongitude());
            // Check if the driver is not null before accessing its properties
            if (ride.getDriver() != null) {
                rideViewDTO.setDriver(ride.getDriver().getFirstName());

                // Check if the vehicle is not null before accessing its properties
                if (ride.getDriver().getVehicle() != null) {
                    rideViewDTO.setVehicleType(ride.getDriver().getVehicle().getVehicleType().getVehicleType());
                    rideViewDTO.setVehicleNo(ride.getDriver().getVehicle().getVehicleNo());
                }
            }
            rideViewDTO.setAmount(ride.getPayment().getPaymentAmount());
            rideViewDTO.setStatus(ride.getStatus());
            rideViewDTO.setCreatedAt(ride.getCreatedAt());
            rideViewDTO.setUpdatedAt(ride.getUpdatedAt());

            rideViewDTOList.add(rideViewDTO);
        }
        return rideViewDTOList;
    }

    public List<RideViewDTO> getRideRequests(UUID userId) {
        List<RideViewDTO> rideViewDTOList = new ArrayList<>();
        List<Ride> rideList = Collections.emptyList();

        Optional<List<Ride>> rideRequests = rideRepository.findRideRequests(userId);
        if (rideRequests.isPresent()) {
            rideList = rideRequests.get();
        }

        for (Ride ride : rideList) {
            RideViewDTO rideViewDTO = new RideViewDTO();

            rideViewDTO.setRideId(ride.getRideId());
            rideViewDTO.setPassenger(ride.getPassenger().getFirstName());
            rideViewDTO.setPickupLocationLatitude(ride.getPickupLocationLatitude());
            rideViewDTO.setPickupLocationLongitude(ride.getPickupLocationLongitude());
            rideViewDTO.setDestinationLocationLatitude(ride.getDestinationLocationLatitude());
            rideViewDTO.setDestinationLocationLongitude(ride.getDestinationLocationLongitude());
            rideViewDTO.setAmount(ride.getPayment().getPaymentAmount());
            rideViewDTO.setStatus(ride.getStatus());
            rideViewDTO.setCreatedAt(ride.getCreatedAt());
            rideViewDTO.setUpdatedAt(ride.getUpdatedAt());

            rideViewDTOList.add(rideViewDTO);
        }
        return rideViewDTOList;
    }

    @Transactional
    public CommonResponse cancelRide(@NotNull Ride ride) {
        ride.setStatus(0); // Update the ride status to 0
        rideRepository.save(ride);

        List<RideDriver> rideDrivers = rideDriverRepository.findRideDriverByRide(ride);
        for (RideDriver rideDriver : rideDrivers) {
            rideDriver.setStatus(3); // Update the ride driver status to 3
        }
        rideDriverRepository.saveAll(rideDrivers);

        return CommonResponse.builder()
                .status(200)
                .message("Ride Cancelled Successfully")
                .build();
    }

    @Transactional
    public CommonResponse changeRide(Ride ride, int status) {
        ride.setStatus(status);
        rideRepository.save(ride);

        if (status == 4) {
            Payment payment = paymentRepository.findByRide(ride);
            payment.setStatus(1);
            paymentRepository.save(payment);
        }

        return CommonResponse.builder()
                .status(200)
                .message("Ride Cancelled Successfully")
                .build();
    }

    @Transactional
    public CommonResponse acceptRide(Ride ride, UUID userId) {

        Optional<User> driver = userRepository.findByUserId(userId);
        ride.setStatus(2);
        ride.setDriver(driver.get());
        rideRepository.save(ride);

        List<RideDriver> rideDrivers = rideDriverRepository.findRideDriverByRide(ride);
        for (RideDriver rideDriver : rideDrivers) {
            if (rideDriver.getDriver().getUserId().equals(userId)) {
                rideDriver.setStatus(2); // Update the ride driver status to 2 if driverId matches
            } else {
                rideDriver.setStatus(4); // Update the ride driver status to 4 for other drivers
            }
        }
        rideDriverRepository.saveAll(rideDrivers);

        return CommonResponse.builder()
                .status(200)
                .message("Ride Cancelled Successfully")
                .build();
    }

    @Transactional
    public CommonResponse rejectRide(Ride ride, UUID userId) {

        boolean pendingFlag = false;
        List<RideDriver> rideDrivers = rideDriverRepository.findRideDriverByRide(ride);
        for (RideDriver rideDriver : rideDrivers) {
            if (rideDriver.getDriver().getUserId().equals(userId)) {
                rideDriver.setStatus(0); // Update the ride driver status to 2 if driverId matches
            }
            if(rideDriver.getStatus() == 1) {
                pendingFlag = true;
            }
        }
        rideDriverRepository.saveAll(rideDrivers);

        if (!pendingFlag) {
            ride.setStatus(5);
        }

        return CommonResponse.builder()
                .status(200)
                .message("Ride Cancelled Successfully")
                .build();
    }
}
