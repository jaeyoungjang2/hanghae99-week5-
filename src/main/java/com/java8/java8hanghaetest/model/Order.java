package com.java8.java8hanghaetest.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.java8.java8hanghaetest.dto.OrderRequestDto;
import com.java8.java8hanghaetest.util.Constants;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @JsonIgnore
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderFood> orderFoods = new ArrayList<>();

    // 최종 음식 주문 금액
    private Long totalPrice;
    // 음식점의 기본 배달료
    private Long baseDeliveryFee;
    // 주문자와 음식점 간의 배달 할증료
    private Long premiumDeliveryFee;

    public Order(List<OrderFood> orderFoods, Restaurant restaurant,
        OrderRequestDto orderRequestDto) {
        this.restaurant = restaurant;
        this.baseDeliveryFee = restaurant.getDeliveryFee();

        for (OrderFood orderFood : orderFoods) {
            orderFood.setOrder(this);
            this.orderFoods.add(orderFood);
        }

        // 음식점과 고객집 간의 배달 거리가 1km 멀어질 때 마다 할증료 추가
        calPremiumDeliveryFee(restaurant, orderRequestDto);

        // 최종 음식 주문 금액 계산
        calTotalPriceAndSetOrder(orderFoods);
        checkValidMinOrderPrice();

        this.orderFoods = orderFoods;
    }

    private void calPremiumDeliveryFee(Restaurant restaurant, OrderRequestDto orderRequestDto) {
        if (restaurant.getX() == null || restaurant.getY() == null) {
            throw new IllegalArgumentException("음식점의 지역정보가 존재하지 않습니다.");
        }
        if (orderRequestDto.getX() == null || orderRequestDto.getY() == null) {
            throw new IllegalArgumentException("주문자의 지역정보가 존재하지 않습니다.");
        }

        Long restaurantX = restaurant.getX();
        Long restaurantY = restaurant.getY();
        Long clientX = orderRequestDto.getX();
        Long clientY = orderRequestDto.getY();
        long distanceFromRestaurant = Math.abs(restaurantX - clientX) + Math.abs(restaurantY - clientY);
        if (distanceFromRestaurant >= Constants.ACCEPTABLE_DELIVERY_DISTANCE) {
            throw new IllegalArgumentException("배달가능한 지역이 아닙니다.");
        }
        this.premiumDeliveryFee =
            distanceFromRestaurant * Constants.PREMIUM_DELIVERY_FEE_BY_DISTANCE;
    }

//    public Order(List<OrderFood> orderFoods, Restaurant restaurant) {
//        this.restaurant = restaurant;
//        this.baseDeliveryFee = restaurant.getDeliveryFee();
//
//        for (OrderFood orderFood : orderFoods) {
//            orderFood.setOrder(this);
//            this.orderFoods.add(orderFood);
//        }
//
//        // 음식점과 고객집 간의 배달 거리가 1km 멀어질 때 마다 할증료 추가
//
//
//        // 최종 음식 주문 금액 계산
//        calTotalPriceAndSetOrder(orderFoods);
//        checkValidMinOrderPrice();
//        this.totalPrice += baseDeliveryFee;
//
//        this.orderFoods = orderFoods;
//    }



    private void checkValidMinOrderPrice() {
        if (this.totalPrice < this.restaurant.getMinOrderPrice()) {
            throw new IllegalArgumentException(
                String.format("최소 배달 주문 금액은 %s 입니다", this.getRestaurant().getMinOrderPrice()));
        }
    }

    private void calTotalPriceAndSetOrder(List<OrderFood> orderFoods) {
        Long totPrice = 0L;
        for (OrderFood orderFood : orderFoods) {
            totPrice += orderFood.getOrderPrice() * orderFood.getOrderCount();
        }
        this.totalPrice = totPrice;
    }
}
