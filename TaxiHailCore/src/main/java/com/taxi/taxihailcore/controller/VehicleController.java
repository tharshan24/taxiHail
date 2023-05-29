package com.taxi.taxihailcore.controller;

import com.taxi.taxihailcore.dto.UserDTO;
import com.taxi.taxihailcore.dto.VehicleDTO;
import com.taxi.taxihailcore.dto.VehicleTypeDTO;
import com.taxi.taxihailcore.model.User;
import com.taxi.taxihailcore.model.VehicleType;
import com.taxi.taxihailcore.repository.UserRepository;
import com.taxi.taxihailcore.exceptions.UserRegistrationException;
import com.taxi.taxihailcore.model.Vehicle;
import com.taxi.taxihailcore.repository.VehicleRepository;
import com.taxi.taxihailcore.response.CommonResponse;
import com.taxi.taxihailcore.service.VehicleService;
import jakarta.servlet.http.HttpServletRequest;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/vehicle")
public class VehicleController {

    private final VehicleService vehicleService;
    private final UserRepository userRepository;
    private final VehicleRepository vehicleRepository;

    public VehicleController(VehicleService vehicleService, UserRepository userRepository, VehicleRepository vehicleRepository){
        this.vehicleService = vehicleService;
        this.userRepository = userRepository;
        this.vehicleRepository = vehicleRepository;
    }

    @GetMapping("/view_vehicle_by_user")
    public ResponseEntity<VehicleDTO> viewVehicleByUSer(
            @NotNull HttpServletRequest request
    ) {
        UUID userId = UUID.fromString((String) request.getAttribute("userId"));
        Optional<User> optionalUser = userRepository.findByUserId(userId);
        if(optionalUser.isPresent()) {
            User user = optionalUser.get();
            Optional<Vehicle> optionalVehicle = vehicleService.viewVehicleByDriver(user);
            if (optionalVehicle.isPresent()) {

                Vehicle vehicle = optionalVehicle.get();
                VehicleDTO vehicleDTO = new VehicleDTO();
                BeanUtils.copyProperties(vehicle, vehicleDTO);

                VehicleTypeDTO vehicleTypeDTO = new VehicleTypeDTO();
                VehicleType vehicleType = vehicle.getVehicleType();
                BeanUtils.copyProperties(vehicleType, vehicleTypeDTO);
                vehicleDTO.setVehicleType(vehicleTypeDTO);

                UserDTO userDTO = new UserDTO();
                BeanUtils.copyProperties(user, userDTO);
                vehicleDTO.setDriver(userDTO);

                return ResponseEntity.ok(vehicleDTO);
            }
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

    @PostMapping("/add_vehicle")
    public ResponseEntity<CommonResponse> addVehicle (@RequestBody @NotNull Vehicle request) {
        // Check if the vehicle already exists in the database
        if (vehicleRepository.existsByVehicleNo(request.getVehicleNo())) {
            throw new UserRegistrationException("Vehicle number already exists");
        }
        return ResponseEntity.ok(vehicleService.addVehicle(request));
    }

    @PostMapping("/update_vehicle")
    public ResponseEntity<CommonResponse> updateVehicle (
            @RequestBody @NotNull Vehicle request,
            @NotNull HttpServletRequest httpServletRequest
    ) {
        UUID vehicleId;
        UUID userId = UUID.fromString((String) httpServletRequest.getAttribute("userId"));
        Optional<User> optionalUser = userRepository.findByUserId(userId);
        if (optionalUser.isPresent()) {

            User user = optionalUser.get();
            Optional<Vehicle> optionalVehicle = vehicleRepository.findByDriverAndStatus(user, 1);

            if (optionalVehicle.isPresent()) {
                Vehicle vehicle = optionalVehicle.get();
                vehicleId = vehicle.getVehicleId();
                if (vehicleRepository.existsByVehicleNo(request.getVehicleNo()) && !request.getVehicleNo().equals(vehicle.getVehicleNo())) {
                    throw new UserRegistrationException("Vehicle No already exists");
                }
            } else {
                throw new UserRegistrationException("Vehicle not found");
            }
        }
        else {
            throw new UserRegistrationException("User not found");
        }
        return ResponseEntity.ok(vehicleService.updateVehicle(request, vehicleId));
    }
}

