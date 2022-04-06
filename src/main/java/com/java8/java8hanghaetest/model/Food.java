package com.java8.java8hanghaetest.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
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

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    private boolean run;

    @JsonIgnore
    @OneToMany(mappedBy = "food", cascade = CascadeType.ALL)
    private List<Option> options = new ArrayList<>();

    @OneToMany
    private Set<Category> categories = new HashSet<>();

    public Food(String foodName, Long foodPrice, Restaurant restaurant) {
        // 음식 등록 가격 확인
        checkValidFoodPrice(foodPrice);

        this.name = foodName;
        this.price = foodPrice;
        this.orderCount = 0L;
        this.run = false;
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

    public void soldOut() {
        this.run = false;
    }

    public void ready() {
        this.run = true;
    }

    public void addCategory(Category category) {
        this.categories.add(category);
        this.restaurant.getCategories().add(category);
    }

    public void addOption(Option option) {
        this.options.add(option);
    }
}
