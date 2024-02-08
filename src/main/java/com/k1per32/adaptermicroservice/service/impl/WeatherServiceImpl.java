package com.k1per32.adaptermicroservice.service.impl;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.k1per32.adaptermicroservice.exception.WeatherServiceException;
import com.k1per32.adaptermicroservice.service.WeatherService;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Service;

import java.io.IOException;


@Service
public class WeatherServiceImpl implements WeatherService {
    @Override
    public String getWeather(String latitude, String longitude) {
        String apiKey = "890042bf-44fb-49d2-aa14-e5970fdf889a";
        String responseData = null;
        String messageB = null;
        OkHttpClient client = new OkHttpClient();

        String apiUrl = "https://api.weather.yandex.ru/v2/forecast/?lat=" + latitude
                + "&lon=" + longitude + "&lang=ru_RU" + "&limit=1" + "&hours=false" + "&extra=false";

        Request request = new Request.Builder()
                .url(apiUrl)
                .addHeader("X-Yandex-API-Key", apiKey)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                responseData = response.body().string();
                System.out.println(responseData);
            } else {
                throw new WeatherServiceException("Не удалось получить погоду: " + response.code());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        ObjectMapper mapper = new ObjectMapper();

        try {
            JsonNode rootNode = mapper.readTree(responseData);
            String txt = rootNode.path("geo_object")
                    .path("province")
                    .path("name")
                    .asText() + ", " +
                    rootNode.path("geo_object")
                            .path("locality")
                            .path("name")
                            .asText();
            if(txt.matches("^[ ,]*$")){
                throw new WeatherServiceException("Пустое сообщение из сервиса погоды, введите правильные координаты");
            }
            String nowDt = rootNode.path("now_dt").asText();
            int temp = rootNode.path("fact").path("temp").asInt();

            ObjectNode outputNode = mapper.createObjectNode();
            outputNode.put("txt", txt);
            outputNode.put("now-dt", nowDt);
            outputNode.put("temp", temp);

            messageB = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(outputNode);
            System.out.println(messageB);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return messageB;
    }
}