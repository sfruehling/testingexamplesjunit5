package com.example.json;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@SuppressWarnings({"FieldCanBeLocal", "unused"})
@JsonSerialize(using = CarDetailsJsonSerializer.class)
@JsonDeserialize(using= CarDetailsJsonDeserializer.class)
public class CarDetails {
    private String manufacturer;
    private String type;
    private String color;

    public CarDetails(String manufacturer, String type, String color) {
        this.manufacturer = manufacturer;
        this.type = type;
        this.color = color;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public String getType() {
        return type;
    }

    public String getColor() {
        return color;
    }
}
