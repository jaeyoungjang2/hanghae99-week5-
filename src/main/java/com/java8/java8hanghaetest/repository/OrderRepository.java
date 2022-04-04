package com.java8.java8hanghaetest.repository;

import com.java8.java8hanghaetest.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
