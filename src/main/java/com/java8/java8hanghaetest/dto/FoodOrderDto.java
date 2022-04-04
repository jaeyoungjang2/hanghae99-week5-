package com.java8.java8hanghaetest.dto;

import com.java8.java8hanghaetest.model.Food;
import com.java8.java8hanghaetest.model.OrderFood;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FoodOrderDto {
    String name;
    Long quantity;
    Long price;

    public FoodOrderDto(OrderFood orderFood) {
        Food food = orderFood.getFood();
        this.name = food.getName();
        this.quantity = orderFood.getOrderCount();
        this.price = orderFood.getOrderTotalPrice();
    }
}
