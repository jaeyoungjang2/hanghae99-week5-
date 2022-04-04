package com.java8.java8hanghaetest.repository;

import com.java8.java8hanghaetest.model.Restaurant;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    
}
