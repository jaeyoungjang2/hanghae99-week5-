package com.java8.java8hanghaetest.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestaurantDto {
    private Long id;
    private String name;
    private Long minOrderPrice;
    private Long deliveryFee;

    public RestaurantDto(String name, Long minOrderPrice, Long deliveryFee) {
        this.name = name;
        this.minOrderPrice = minOrderPrice;
        this.deliveryFee = deliveryFee;
    }
}
