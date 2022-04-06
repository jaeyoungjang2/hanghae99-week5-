package com.java8.java8hanghaetest.dto;

import com.java8.java8hanghaetest.model.Order;
import com.java8.java8hanghaetest.model.OrderFood;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDto {
    private String restaurantName;
    private List<FoodOrderDto> foods = new ArrayList<>();
    private Long deliveryFee;
    private Long premiumDeliveryFee;
    private Long totalPrice;

    public OrderDto(Order order) {
        this.restaurantName = order.getRestaurant().getName();

        List<OrderFood> orderFoods = order.getOrderFoods();
        for (OrderFood orderFood : orderFoods) {
            FoodOrderDto foodOrderDto = new FoodOrderDto(orderFood);
            foods.add(foodOrderDto);
        }

        this.deliveryFee = order.getBaseDeliveryFee();
        this.premiumDeliveryFee = order.getPremiumDeliveryFee();
        this.totalPrice = order.getTotalPrice() + this.deliveryFee + this.premiumDeliveryFee;
    }



    public void addTotalPrice(Long totalPrice) {
        this.totalPrice += totalPrice;
    }
}
