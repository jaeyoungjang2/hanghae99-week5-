package com.java8.java8hanghaetest.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.java8.java8hanghaetest.dto.RestaurantDto;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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

    @JsonIgnore
    @OneToMany(mappedBy = "restaurant")
    private List<Food> foods = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "restaurant")
    private List<Order> orders = new ArrayList<>();

    public Restaurant(RestaurantDto restaurantDto){
        this.name = restaurantDto.getName();
        this.minOrderPrice=restaurantDto.getMinOrderPrice();
        this.deliveryFee=restaurantDto.getDeliveryFee();
    }
}
