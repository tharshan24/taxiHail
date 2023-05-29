package com.taxi.taxihailcore.service;

import com.taxi.taxihailcore.model.User;
import com.taxi.taxihailcore.repository.UserRepository;
import com.taxi.taxihailcore.repository.VehicleTypeRepository;
import com.taxi.taxihailcore.response.AuthenticationResponse;
import com.taxi.taxihailcore.model.Vehicle;
import com.taxi.taxihailcore.repository.VehicleRepository;
import com.taxi.taxihailcore.response.CommonResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class VehicleService {
    private final VehicleRepository vehicleRepository;
    private final VehicleTypeRepository vehicleTypeRepository;
    private final UserRepository userRepository;

    public VehicleService(VehicleRepository vehicleRepository, VehicleTypeRepository vehicleTypeRepository, UserRepository userRepository){
        this.vehicleRepository = vehicleRepository;
        this.vehicleTypeRepository = vehicleTypeRepository;
        this.userRepository = userRepository;
    }

    public Optional<Vehicle> viewVehicleByDriver(User user) {
        return vehicleRepository.findByDriverAndStatus(user, 1);
    }

    public CommonResponse addVehicle(Vehicle request) {
        try{
            var vehicle = Vehicle.builder()
                    .vehicleNo(request.getVehicleNo())
                    .vehicleType(request.getVehicleType())
                    .driver(request.getDriver())
                    .status(1)
                    .build();

            vehicleRepository.save(vehicle);

            return CommonResponse.builder()
                    .status(200)
                    .message("Vehicle registration successful")
                    .build();

        } catch (Exception e) {
            return CommonResponse.builder()
                    .status(0)
                    .message("Vehicle Registration Failed: " + e.getMessage())
                    .build();
        }
    }

    public CommonResponse updateVehicle(Vehicle request, UUID vehicleId) {
        try{

            vehicleRepository.findByVehicleIdAndStatus(vehicleId, 1).ifPresent(vehicle -> {
                vehicle.setVehicleType(request.getVehicleType());
                vehicle.setVehicleNo(request.getVehicleNo());
                vehicleRepository.save(vehicle);
            });

            return CommonResponse.builder()
                    .status(200)
                    .message("Vehicle Update successful")
                    .build();

        } catch (Exception e) {
            return CommonResponse.builder()
                    .status(0)
                    .message("Vehicle Update failed")
                    .build();
        }
    }
}
