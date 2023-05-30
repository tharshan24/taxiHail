package com.taxi.taxihailcore.service;

import com.taxi.taxihailcore.dto.VehicleTypeDTO;
import com.taxi.taxihailcore.model.VehicleType;
import com.taxi.taxihailcore.repository.VehicleTypeRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VehicleTypeService {
    private final VehicleTypeRepository vehicleTypeRepository;

    public VehicleTypeService(VehicleTypeRepository vehicleTypeRepository){
        this.vehicleTypeRepository = vehicleTypeRepository;
    }

    public ResponseEntity getVehicleTypes() {
        List<VehicleTypeDTO> vehicleTypeDTOList = new ArrayList<VehicleTypeDTO>();
        List<VehicleType> vehicleTypes = vehicleTypeRepository.findAll();
        for (VehicleType vehicleType : vehicleTypes) {

            VehicleTypeDTO vehicleTypeDTO = new VehicleTypeDTO();

            BeanUtils.copyProperties(vehicleType, vehicleTypeDTO);

            vehicleTypeDTOList.add(vehicleTypeDTO);
        }
        return ResponseEntity.ok(vehicleTypeDTOList);
    }
}
