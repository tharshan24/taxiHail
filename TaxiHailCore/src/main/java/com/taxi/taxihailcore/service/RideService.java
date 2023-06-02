package com.taxi.taxihailcore.service;

import com.taxi.taxihailcore.dto.RideDTO;
import com.taxi.taxihailcore.dto.RideRequestDTo;
import com.taxi.taxihailcore.model.Ride;
import com.taxi.taxihailcore.model.User;
import com.taxi.taxihailcore.model.Vehicle;
import com.taxi.taxihailcore.repository.RideRepository;
import com.taxi.taxihailcore.response.CommonResponse;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class RideService {
    private final RideRepository rideRepository;

    public RideService(RideRepository rideRepository){
        this.rideRepository = rideRepository;
    }

    public CommonResponse rideRequest(RideRequestDTo rideRequestDTo, User passenger) {

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

            rideRepository.save(ride);

            return CommonResponse.builder()
                    .status(200)
                    .message("Ride request successful")
                    .build();

        } catch (Exception e) {
            return CommonResponse.builder()
                    .status(0)
                    .message("Ride request Failed: " + e.getMessage())
                    .build();
        }
    }
}
