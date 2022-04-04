package com.java8.java8hanghaetest.service;

import com.java8.java8hanghaetest.dto.FoodOrderDto;
import com.java8.java8hanghaetest.dto.FoodOrderRequestDto;
import com.java8.java8hanghaetest.dto.OrderDto;
import com.java8.java8hanghaetest.dto.OrderRequestDto;
import com.java8.java8hanghaetest.model.Food;
import com.java8.java8hanghaetest.model.Order;
import com.java8.java8hanghaetest.model.OrderFood;
import com.java8.java8hanghaetest.model.Restaurant;
import com.java8.java8hanghaetest.repository.FoodRepository;
import com.java8.java8hanghaetest.repository.OrderRepository;
import com.java8.java8hanghaetest.repository.RestaurantRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final RestaurantRepository restaurantRepository;
    private final FoodRepository foodRepository;
    private final OrderRepository orderRepository;

    public OrderDto order(OrderRequestDto orderRequestDto) {

        // orderRequestDto 에는 레스토랑id와 주문 음식 목록이 들어있다.
        Long restaurantId = orderRequestDto.getRestaurantId();

        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(
            () -> new IllegalArgumentException("음식점이 존재하지 않습니다.")
        );

        // 고객이 주문한 음식 리스트
        List<FoodOrderRequestDto> foodOrderRequestDtos = orderRequestDto.getFoods();
        List<OrderFood> orderFoods = new ArrayList<>();
        for (FoodOrderRequestDto foodOrderRequestDto : foodOrderRequestDtos) {
            // 고객이 주문한 음식 확인
            Long id = foodOrderRequestDto.getId();
            Food food = foodRepository.findById(id).get();
            // 고객이 입장한 레스토랑에서 해당 음식이 존재하는지 확인한다.
            List<Food> foods = foodRepository.findByIdAndRestaurant(id, restaurant);
            if (foods.size() == 0) {
                throw new IllegalArgumentException("고객이 주문한 음식이 존재하지 않습니다.");
            }

            // 고객이 주문한 음식 수량 확인
            Long foodQuantity = foodOrderRequestDto.getQuantity();
            checkValidFoodQuantity(foodQuantity);

            // 조건에 만족한다면 주문을 진행합니다.
            // 조건이란 고객이 입장한 레스토랑에 음식이 존재하고,
            // 고객이 주문한 음식의 수량이 최소 조건과 최대 조건을 만족하는 경우입니다.

            // 주문을 진행합니다.
            // 음식 엔티티에 주문한 메뉴 수량이 올라가고

            // 주문한 메뉴와 수량 저장
            OrderFood orderFood = new OrderFood(food, foodQuantity, restaurant.getDeliveryFee());
            orderFoods.add(orderFood);
            System.out.println("order foods size");
            System.out.println(orderFoods.size());
        }
        System.out.println("total order oods size");
        System.out.println(orderFoods.size());
        Order order = new Order(orderFoods, restaurant);
        orderRepository.save(order);


        OrderDto orderDto = new OrderDto(order);
        return orderDto;
    }

    private void checkValidFoodQuantity(Long foodQuantity) {
        if (foodQuantity < 1 || foodQuantity > 100) {
            throw new IllegalArgumentException("음식의 최소 주문 수량은 1개이고, 최대 주문 수량은 100개 입니다.");
        }
    }

    public List<OrderDto> getAllOrder() {
        List<Order> orders = orderRepository.findAll();
        List<OrderDto> orderDtos = new ArrayList<>();
        for (Order order : orders) {
            OrderDto orderDto = new OrderDto(order);
            orderDtos.add(orderDto);
        }
        return orderDtos;
    }
}
