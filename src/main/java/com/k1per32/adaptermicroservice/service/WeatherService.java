package com.k1per32.adaptermicroservice.service;


import java.io.IOException;

public interface WeatherService {
    String getWeather(String latitude, String longitude) throws IOException;
}