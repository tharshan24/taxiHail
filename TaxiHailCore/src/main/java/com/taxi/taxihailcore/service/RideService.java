package com.taxi.taxihailcore.service;

import com.taxi.taxihailcore.dto.RideRequestDTo;
import com.taxi.taxihailcore.model.Ride;
import com.taxi.taxihailcore.model.RideDriver;
import com.taxi.taxihailcore.model.User;
import com.taxi.taxihailcore.repository.RideDriverRepository;
import com.taxi.taxihailcore.repository.RideRepository;
import com.taxi.taxihailcore.repository.UserRepository;
import com.taxi.taxihailcore.response.CommonResponse;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RideService {
    private final RideRepository rideRepository;
    private final UserRepository userRepository;
    private final RideDriverRepository rideDriverRepository;

    public RideService(RideRepository rideRepository, UserRepository userRepository, RideDriverRepository rideDriverRepository){
        this.rideRepository = rideRepository;
        this.userRepository = userRepository;
        this.rideDriverRepository = rideDriverRepository;
    }

    public CommonResponse rideRequest(RideRequestDTo rideRequestDTo, User passenger, String authHeader) {

        String[] pickupParts = rideRequestDTo.getPickupLocation().split(",");
        String[] destinationParts = rideRequestDTo.getDestinationLocation().split(",");

        try{
            var ride = Ride.builder()
                    .pickupLocationLatitude(new BigDecimal(pickupParts[0]))
                    .pickupLocationLongitude(new BigDecimal(pickupParts[1]))
                    .destinationLocationLatitude(new BigDecimal(destinationParts[0]))
                    .destinationLocationLongitude(new BigDecimal(destinationParts[1]))
                    .passenger(passenger)
                    .status(1)
                    .build();

            Ride savedRide = rideRepository.save(ride);

            //make REST request to location server
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", authHeader );

            HttpEntity<RideRequestDTo> requestEntity = new HttpEntity<>(rideRequestDTo, headers);

            RestTemplate restTemplate = new RestTemplate();
            String url = "http://localhost:8081/location/get_available_drivers";

            // Define the type reference for a list of UUIDs
            ParameterizedTypeReference<List<UUID>> responseType = new ParameterizedTypeReference<List<UUID>>() {};
            // Make the REST request
            ResponseEntity<List<UUID>> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, responseType);

            // Process the response
            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                List<UUID> driverIdList = responseEntity.getBody();

                if (driverIdList.isEmpty())
                {
                    return CommonResponse.builder()
                            .status(0)
                            .message("No drivers available !")
                            .build();
                }

                List<RideDriver> rideDriverList = new ArrayList<>();

                for ( UUID driverId :driverIdList ) {
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
                    .message("Ride request Failed: " + e.getMessage())
                    .build();
        }
    }
}
