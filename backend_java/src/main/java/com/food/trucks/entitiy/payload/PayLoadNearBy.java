package com.food.trucks.entitiy.payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PayLoadNearBy {
    @NotNull(message = "lat not be null")
    @NotBlank(message = "lat not be empty")
    private String lat;
    @NotNull(message = "lng not be null")
    @NotBlank(message = "lng not be empty")
    private String lng;
    private String name;
}

