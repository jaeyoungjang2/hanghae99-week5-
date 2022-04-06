package com.java8.java8hanghaetest.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryDto {

    private Long foodId;
    private Long restaurantId;

    private String categoryName;
}
