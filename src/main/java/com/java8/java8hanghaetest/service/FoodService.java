package com.java8.java8hanghaetest.service;

import com.java8.java8hanghaetest.model.Food;
import com.java8.java8hanghaetest.model.Restaurant;
import com.java8.java8hanghaetest.repository.FoodRepository;
import com.java8.java8hanghaetest.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FoodService {

    private final FoodRepository foodRepository;
    private final RestaurantRepository restaurantRepository;

    public Food soldOut(Long restaurantId, Long foodId) {

        Restaurant restaurant = isExistRestaurant(restaurantId);
        Food food = isExistFood(foodId);
        existFoodInRestaurant(restaurant, food);

        food.soldOut();
        return food;
    }

    public Food ready(Long restaurantId, Long foodId) {
        Restaurant restaurant = isExistRestaurant(restaurantId);
        Food food = isExistFood(foodId);
        existFoodInRestaurant(restaurant, food);

        food.ready();
        return food;
    }

    private Restaurant isExistRestaurant(Long restaurantId) {
        return restaurantRepository.findById(restaurantId).orElseThrow(
            () -> new IllegalArgumentException("레스토랑이 존재하지 않습니다.")
        );
    }

    private void existFoodInRestaurant(Restaurant restaurant, Food food) {

        if (!food.getRestaurant().getId().equals(restaurant.getId())) {
            throw new IllegalArgumentException("해당 음식점에 존재하지 않는 음식입니다.");
        }
    }

    private Food isExistFood(Long foodId) {
        Food food = foodRepository.findById(foodId).orElseThrow(
            () -> new IllegalArgumentException("존재하지 않는 음식은 판매 금지 지정을 할 수 없습니다.")
        );
        return food;
    }
}
