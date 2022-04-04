package com.java8.java8hanghaetest.service;

import com.java8.java8hanghaetest.dto.RestaurantDto;
import com.java8.java8hanghaetest.dto.RestaurantFoodDto;
import com.java8.java8hanghaetest.model.Food;
import com.java8.java8hanghaetest.model.Restaurant;
import com.java8.java8hanghaetest.repository.FoodRepository;
import com.java8.java8hanghaetest.repository.RestaurantRepository;
import java.util.List;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final FoodRepository foodRepository;

    public Restaurant enroll(RestaurantDto restaurantDto) {
        Restaurant restaurant = new Restaurant(restaurantDto);

        Long minOrderPrice = restaurant.getMinOrderPrice();
        Long deliveryFee = restaurant.getDeliveryFee();

        // 주문 금액 확인
        checkMinOrderPrice(minOrderPrice);
        // 배달 금액 확인
        checkValidDeliveryFee(deliveryFee);

        return restaurantRepository.save(restaurant);
    }

    private void checkValidDeliveryFee(Long deliveryFee) {
        // 최소 배달금액, 최대 밷라금액 확인
        if (deliveryFee < 0 || deliveryFee > 10000) {
            throw new IllegalArgumentException("배달비의 최소 금액은 0원이며, 최대 금액은 10,000원 입니다.");
        }

        // 배달 금액의 단위 확인
        if (deliveryFee % 500 != 0) {
            throw new IllegalArgumentException("배송비는 500원 단위로 등록할 수 있습니다.");
        }
    }

    private void checkMinOrderPrice(Long minOrderPrice) {
        // 최소 주문 가격
        if (minOrderPrice < 1000 || minOrderPrice > 100000) {
            throw new IllegalArgumentException("최소 주문 가격은 1,000원, 최대 주문가격은 100,000원 입니다.");
        }

        // 주문 가격은 100원 단위
        if (minOrderPrice % 100 != 0) {
            throw new IllegalArgumentException("주문 가격은 100원 단위입니다..");
        }
    }

    public List<Restaurant> getAllRestaurant() {
        return restaurantRepository.findAll();
    }

    @Transactional
    public void registFood(Long restaurantId, List<RestaurantFoodDto> restaurantFoodDtos) {
        // 음식과 음식가격을 받아서 레스토랑에 등록한다. (레스토랑은 restaurantId로 찾는다.)
        for (RestaurantFoodDto restaurantFoodDto : restaurantFoodDtos) {
            String foodName = restaurantFoodDto.getName();
            Long foodPrice = restaurantFoodDto.getPrice();

            Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(
                () -> new IllegalArgumentException("레스토랑 id가 존재하지 않습ㄴ다.")
            );

            checkValidFoodName(foodName, restaurant);

            // 중복된 음식이 없으면 저장
            Food food = new Food(foodName, foodPrice, restaurant);
            foodRepository.save(food);
        }
    }

    private void checkValidFoodName(String foodName, Restaurant restaurant) {
        // 등록한 레스토랑의 음식점 메뉴 확인
        List<Food> foods = restaurant.getFoods();
        for (Food food : foods) {
            if (food.getName().equals(foodName)) {
                throw new IllegalArgumentException("같은 음식점 내에서는 음식 이름이 중복될 수 없습니다.");
            }
        }
    }



    public List<Food> getMenu(Long restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(
            () -> new IllegalArgumentException("입력하신 레스토랑이 존재하지 않습니다.")
        );

        return restaurant.getFoods();

    }
}
