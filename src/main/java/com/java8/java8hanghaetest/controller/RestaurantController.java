package com.java8.java8hanghaetest.controller;

import com.java8.java8hanghaetest.dto.RestaurantDto;
import com.java8.java8hanghaetest.dto.RestaurantFoodDto;
import com.java8.java8hanghaetest.model.Food;
import com.java8.java8hanghaetest.model.Restaurant;
import com.java8.java8hanghaetest.service.RestaurantService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class RestaurantController {

    private final RestaurantService restaurantService;

    // 음식점 등록
    @PostMapping("/restaurant/register")
    public Restaurant enrollRestaurant(@RequestBody RestaurantDto restaurantDto) {
        return restaurantService.enroll(restaurantDto);
    }

    // 등록된 음식점 모두 조회
    @GetMapping("/restaurants")
    public List<Restaurant> getAllRestaurant() {
        return restaurantService.getAllRestaurant();
    }

    // 음식 등록
    @PostMapping("/restaurant/{restaurantId}/food/register")
    public void registFood(@PathVariable Long restaurantId, @RequestBody List<RestaurantFoodDto> restaurantFoodDtos) {
        // 음식과 음식가격을 받아서 레스토랑에 등록한다. (레스토랑은 restaurantId로 찾는다.)
        restaurantService.registFood(restaurantId, restaurantFoodDtos);
    }

    // 메뉴판 조회
    @GetMapping("/restaurant/{restaurantId}/foods")
    public List<Food> getMenu(@PathVariable Long restaurantId) {
        return restaurantService.getMenu(restaurantId);
    }
}
