package com.k1per32.adaptermicroservice.model;

import lombok.Data;

@Data
public class MessageB {
    private String txt;
    private String createdDt;
    private Integer currentTemp;

    public MessageB(String txt, String createdDt, Integer currentTemp) {
        this.txt = txt;
        this.createdDt = createdDt;
        this.currentTemp = currentTemp;
    }
}