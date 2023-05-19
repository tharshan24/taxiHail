package com.taxi.taxihailcore.service;

import com.taxi.taxihailcore.repository.VehicleTypeRepository;
import org.springframework.stereotype.Service;

@Service
public class VehicleTypeService {
    private final VehicleTypeRepository vehicleTypeRepository;

    public VehicleTypeService(VehicleTypeRepository vehicleTypeRepository){
        this.vehicleTypeRepository = vehicleTypeRepository;
    }
}
