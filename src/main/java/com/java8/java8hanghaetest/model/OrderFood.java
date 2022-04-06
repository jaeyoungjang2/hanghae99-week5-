package com.java8.java8hanghaetest.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class OrderFood {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "food_id")
    private Food food;

    // 주문당시 가격
    private Long orderPrice;
    // 주문당시 수량
    private Long orderCount;
    // 주문당시 판매 금액
    private Long orderTotalPrice;
    private Long deliveryFee;

    public OrderFood(Food food, Long foodQuantity, Long deliveryFee) {
        this.orderPrice = food.getPrice();
        this.orderCount = foodQuantity;
        this.orderTotalPrice = this.orderPrice * this.orderCount;
        this.deliveryFee = deliveryFee;
        this.food = food;

        food.addOrderCount(foodQuantity);
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
