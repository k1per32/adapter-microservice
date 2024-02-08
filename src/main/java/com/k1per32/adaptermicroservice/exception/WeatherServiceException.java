package com.k1per32.adaptermicroservice.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class WeatherServiceException extends RuntimeException {
    private String message;

}