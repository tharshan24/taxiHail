package com.taxi.taxihailcore.dto;

import com.taxi.taxihailcore.model.VehicleType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RideRequestDTo {
    private VehicleType vehicleType;
    private String pickupLocation;
    private String destinationLocation;
}
