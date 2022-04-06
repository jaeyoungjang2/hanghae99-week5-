package com.java8.java8hanghaetest.controller;

import com.java8.java8hanghaetest.dto.RestaurantFoodDto;
import com.java8.java8hanghaetest.model.Food;
import com.java8.java8hanghaetest.service.FoodService;
import com.java8.java8hanghaetest.service.RestaurantService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FoodController {

    private final RestaurantService restaurantService;
    private final FoodService foodService;

    // 메뉴(음식) 등록
    // @Secured(value = UserRoleEnum.Authority.ADMIN)
    @PostMapping("/restaurant/{restaurantId}/food/register")
    public void registFood(@PathVariable Long restaurantId, @RequestBody List<RestaurantFoodDto> restaurantFoodDtos) {
        // 음식과 음식가격을 받아서 레스토랑에 등록한다. (레스토랑은 restaurantId로 찾는다.)
        restaurantService.registFood(restaurantId, restaurantFoodDtos);
    }

    // 메뉴판 조회
    // @Secured(value = UserRoleEnum.Authority.USER)
    @GetMapping("/restaurant/{restaurantId}/foods")
    public List<Food> getMenu(@PathVariable Long restaurantId) {
        return restaurantService.getMenu(restaurantId);
    }

    // 메뉴 정지 (메뉴 재고 소진으로 인하여 판매 종료)
    @PutMapping("/restaurant/{restaurantId}/{foodId}/closed")
    public Food soldOut(
        @PathVariable Long restaurantId,
        @PathVariable Long foodId) {
        return foodService.soldOut(restaurantId, foodId);
    }

    // 메뉴 시작 (재고가 존재할 경우 다시 판매 시작)
    @PutMapping("/restaurant/{restaurantId}/{foodId}/open")
    public Food ready(
        @PathVariable Long restaurantId,
        @PathVariable Long foodId) {
        return foodService.ready(restaurantId, foodId);
    }
}
