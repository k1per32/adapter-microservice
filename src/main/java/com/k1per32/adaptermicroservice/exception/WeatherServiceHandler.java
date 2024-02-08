package com.k1per32.adaptermicroservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class WeatherServiceHandler {
    @ExceptionHandler(WeatherServiceException.class)
    public ResponseEntity<?> notValidData(WeatherServiceException exception) {
        Map<String, Object> body = new HashMap<>();
        body.put("error", exception.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(body);
    }
}
