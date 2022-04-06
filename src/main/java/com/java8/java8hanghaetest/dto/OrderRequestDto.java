package com.java8.java8hanghaetest.dto;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderRequestDto {
    private Long restaurantId;
    private List<FoodOrderRequestDto> foods = new ArrayList<>();
    // 주문자의 위치
    private Long x;
    private Long y;
}
