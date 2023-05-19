package com.taxi.taxihailcore.controller;

import com.taxi.taxihailcore.service.VehicleTypeService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/vehicle_types")
public class VehicleTypeController {

    private final VehicleTypeService vehicleTypeService;

    public VehicleTypeController(VehicleTypeService vehicleTypeService){
        this.vehicleTypeService = vehicleTypeService;
    }
}
