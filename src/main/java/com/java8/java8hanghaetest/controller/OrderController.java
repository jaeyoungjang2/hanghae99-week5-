package com.java8.java8hanghaetest.controller;

import com.java8.java8hanghaetest.dto.OrderDto;
import com.java8.java8hanghaetest.dto.OrderRequestDto;
import com.java8.java8hanghaetest.model.UserRoleEnum;
import com.java8.java8hanghaetest.service.OrderService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    // 주문
    @Secured(value = UserRoleEnum.Authority.USER)
    @PostMapping("/order/request")
    public OrderDto orderMenu(@RequestBody OrderRequestDto orderDto) {
        // orderDto에는 레스토랑id 와 주문 음식 목록이 들어있다.
        System.out.println("start order request");
        return orderService.order(orderDto);
    }

    @Secured(value = UserRoleEnum.Authority.ADMIN)
    @GetMapping("/orders")
    public List<OrderDto> getOrders() {
        // 그동안 성공한 모든 주문 요청을 조회
        return orderService.getAllOrder();
    }
}
