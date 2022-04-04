package com.java8.java8hanghaetest.repository;

import com.java8.java8hanghaetest.model.Food;
import com.java8.java8hanghaetest.model.Restaurant;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodRepository extends JpaRepository<Food, Long> {

//    List<Food> findByRestaurant(Restaurant restaurant);

    List<Food> findByIdAndRestaurant(Long id, Restaurant restaurant);
}
