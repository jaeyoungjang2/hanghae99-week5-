package com.java8.java8hanghaetest.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor // db에서 꺼낼때 필요한가봄
public class Food {
    @Id
    @GeneratedValue
    @Column(name = "food_id")
    private Long id;

    private String name;
    private Long price;
    private Long orderCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    public Food(String foodName, Long foodPrice, Restaurant restaurant) {
        // 음식 등록 가격 확인
        checkValidFoodPrice(foodPrice);

        this.name = foodName;
        this.price = foodPrice;
        this.orderCount = 0L;
        setRestaurant(restaurant);
    }

    private void checkValidFoodPrice(Long foodPrice) {
        // 음식값의 최소 금액은 100원 최대 금액은 1000000
        if (foodPrice < 100 || foodPrice > 1000000) {
            throw new IllegalArgumentException("음식값의 최소 금액은 100원 최대 금액은 1000000");
        }

        // 음식 가격은 100원 단위로만 입력 가능
        if (foodPrice % 100 != 0) {
            throw new IllegalArgumentException("음식 가격은 100원 단위로 입력할 수 있습니다.");
        }
    }

    private void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
        restaurant.getFoods().add(this);
    }

    public void addOrderCount(Long foodQuantity) {
        this.orderCount += foodQuantity;
    }
}
