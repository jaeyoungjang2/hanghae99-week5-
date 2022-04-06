package com.java8.java8hanghaetest.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.java8.java8hanghaetest.dto.RestaurantDto;
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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Entity
public class Restaurant {
    @GeneratedValue
    @Id
    @Column(name = "restaurant_id")
    private Long id;

    private String name;
    private Long minOrderPrice;
    private Long deliveryFee;
    private Long x;
    private Long y;

    // 영업 상태
    private boolean run;

    @JsonIgnore
    @OneToMany(mappedBy = "restaurant")
    private List<Food> foods = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "restaurant")
    private List<Order> orders = new ArrayList<>();


    @OneToMany
    private Set<Category> categories = new HashSet<>();

    public Restaurant(RestaurantDto restaurantDto) {
        this.name = restaurantDto.getName();
        this.minOrderPrice=  restaurantDto.getMinOrderPrice();
        this.deliveryFee = restaurantDto.getDeliveryFee();
        this.x = restaurantDto.getX();
        this.y = restaurantDto.getY();
        this.run = false;
    }


    public void close() {
        this.run = false;
    }

    public void open() {
        this.run = true;
    }
}
