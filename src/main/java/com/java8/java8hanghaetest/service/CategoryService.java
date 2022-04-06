package com.java8.java8hanghaetest.service;

import com.java8.java8hanghaetest.dto.CategoryDto;
import com.java8.java8hanghaetest.model.Category;
import com.java8.java8hanghaetest.model.Food;
import com.java8.java8hanghaetest.model.Restaurant;
import com.java8.java8hanghaetest.repository.CategoryRepository;
import com.java8.java8hanghaetest.repository.FoodRepository;
import com.java8.java8hanghaetest.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CategoryService {

    private final RestaurantRepository restaurantRepository;
    private final FoodRepository foodRepository;
    private final CategoryRepository categoryRepository;

    public Food addCategory(CategoryDto categoryDto) {
        // 음식점의 주인이 맞는지 확인
        // 카테고리 추가
        String categoryName = categoryDto.getCategoryName();
        Long foodId = categoryDto.getFoodId();
        Long restaurantId = categoryDto.getRestaurantId();

        isExistRestaurant(restaurantId);
        Food food = isExistFood(foodId);

        // 카테고리가 없으면 생성해서 food에 추가하고
        // 카테고리가 있으면 찾아서 food에 추가하고



        if (categoryRepository.findByCategoryName(categoryName).size() == 0) {
            Category category = new Category(categoryName);
            food.addCategory(category);
            categoryRepository.save(category);
        } else {
            Category category = categoryRepository.findByCategoryName(categoryName).get(0);
            food.addCategory(category);
        }

        return food;
    }

    private Food isExistFood(Long foodId) {
        return foodRepository.findById(foodId).orElseThrow(
            () -> new IllegalArgumentException("음식이 존재하지 않아 카테고리를 추가할 수 없습니다.")
        );
    }

    private void isExistRestaurant(Long restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(
            () -> new IllegalArgumentException("음식점이 존재하지 않아 카테고리를 추가할 수 없습니다.")
        );
    }
}
