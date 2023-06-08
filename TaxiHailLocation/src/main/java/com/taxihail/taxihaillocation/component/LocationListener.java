package com.taxihail.taxihaillocation.component;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.taxihail.taxihaillocation.dto.LocationDTO;
import com.taxihail.taxihaillocation.model.Location;
import com.taxihail.taxihaillocation.service.LocationService;
import org.springframework.beans.BeanUtils;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class LocationListener {

    private final LocationService locationService;

    public LocationListener(LocationService locationService) {
        this.locationService = locationService;
    }

    @KafkaListener(topics = "location")
    public void listen(String message) throws JsonProcessingException {
//        System.out.println("Location: " + message);
        Gson gson = new Gson();
        LocationDTO locationDTO = gson.fromJson(message, LocationDTO.class);

        Location location = new Location();

        UUID defaultUUID = UUID.fromString("00000000-0000-0000-0000-000000000000");

        location.setDriver(locationDTO.getDriver());

        if(!locationDTO.getVehicleType().equals(defaultUUID) && !locationDTO.getVehicle().equals(defaultUUID)) {
            location.setVehicle(locationDTO.getVehicle());
            location.setVehicleType(locationDTO.getVehicleType());
        }

        location.setLocationLatitude(locationDTO.getLocationLatitude());
        location.setLocationLongitude(locationDTO.getLocationLongitude());
        location.setInRide(locationDTO.getInRide());

        if(!locationDTO.getRide().equals(defaultUUID)) {
            location.setRide(locationDTO.getRide());
        }
        location.setStatus(locationDTO.getStatus());
        location.setCreatedAt(locationDTO.getCreatedAt());

//        BeanUtils.copyProperties(locationDTO, location);
//        System.out.println("DTO: " + location.toString());
        locationService.addLocation(location);
    }
}
