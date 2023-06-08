package com.taxi.taxihailcore.exceptionhandler;

import com.taxi.taxihailcore.exceptions.UserRegistrationException;
import com.taxi.taxihailcore.exceptions.VehicleException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class VehicleExceptionHandler {

    @ExceptionHandler(VehicleException.class)
    public ResponseEntity<String> handleVehicleException(VehicleException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
}

