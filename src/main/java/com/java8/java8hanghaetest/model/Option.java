package com.java8.java8hanghaetest.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Getter;

@Entity
@Getter
public class Option {
    @Id
    @GeneratedValue
    private Long id;

    // 옵션 이름
    private String name;

    // 추가 옵션 가격
    private Long price;

    // 음식
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "food_id")
    private Food food;


    public Option(Food food, String optionName, Long optionPrice) {
        this.food = food;
        this.name = optionName;
        this.price = optionPrice;
    }
}
