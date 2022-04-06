package com.java8.java8hanghaetest.dto;

import com.java8.java8hanghaetest.model.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestaurantDto {
    private Long id;
    private String name;
    private Long minOrderPrice;
    private Long deliveryFee;
    // 음식점의 위치
    private Long x;
    private Long y;

    // 음식점 주인
    private User user;
    // 영업 상태
    private boolean run;
}
