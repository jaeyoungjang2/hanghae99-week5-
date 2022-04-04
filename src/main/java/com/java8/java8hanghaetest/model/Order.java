package com.java8.java8hanghaetest.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    private Long totalPrice;
    private Long deliveryFee;

    public Order(List<OrderFood> orderFoods, Restaurant restaurant) {
        this.restaurant = restaurant;
        this.deliveryFee = restaurant.getDeliveryFee();

        for (OrderFood orderFood : orderFoods) {
            orderFood.setOrder(this);
            this.orderFoods.add(orderFood);
        }

        // 최종 음식 주문 금액 계산 및, order
        calTotalPriceAndSetOrder(orderFoods);
        checkValidMinOrderPrice();
        this.totalPrice += deliveryFee;

        this.orderFoods = orderFoods;
    }

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
