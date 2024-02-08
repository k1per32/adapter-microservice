package com.k1per32.adaptermicroservice.controller;

import com.k1per32.adaptermicroservice.exception.WeatherServiceException;
import com.k1per32.adaptermicroservice.service.WeatherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/adapter")
public class AdapterController {

    private final WeatherService weatherService;

    @Operation(
            summary = "Погода в регионе по заданной широте и долготе",
            tags = {"Weather service"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful Operation", content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)))),
            @ApiResponse(responseCode = "400", description = "No valid data", content = @Content(array = @ArraySchema(schema = @Schema(implementation = WeatherServiceException.class)))),
    })
    @GetMapping("/messages/{lat}/{lon}")
    public ResponseEntity<String> processMessage(@PathVariable("lat") String latitude,
                               @PathVariable("lon") String longitude) throws IOException {
        return ResponseEntity.ok(weatherService.getWeather(latitude, longitude));
    }
}