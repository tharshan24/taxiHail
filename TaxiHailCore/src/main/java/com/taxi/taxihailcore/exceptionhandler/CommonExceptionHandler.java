package com.taxi.taxihailcore.exceptionhandler;

import com.taxi.taxihailcore.exceptions.CommonException;
import com.taxi.taxihailcore.exceptions.UserRegistrationException;
import com.taxi.taxihailcore.exceptions.VehicleException;
import com.taxi.taxihailcore.response.CommonResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CommonExceptionHandler {

    @ExceptionHandler(CommonException.class)
    public ResponseEntity<CommonResponse> handleCommonException(CommonException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(CommonResponse.builder()
                        .status(0)
                        .message(ex.getMessage())
                        .build());
    }
}

