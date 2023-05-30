package com.taxi.taxihailcore.controller;

import com.taxi.taxihailcore.service.VehicleTypeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/vehicle_type")
public class VehicleTypeController {

    private final VehicleTypeService vehicleTypeService;

    public VehicleTypeController(VehicleTypeService vehicleTypeService){
        this.vehicleTypeService = vehicleTypeService;
    }

    @GetMapping("/get_vehicle_types")
    public ResponseEntity getVehicleTypes () {
        return ResponseEntity.ok(vehicleTypeService.getVehicleTypes());
    }
}
