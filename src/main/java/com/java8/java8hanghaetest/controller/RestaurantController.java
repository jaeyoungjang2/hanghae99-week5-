package com.java8.java8hanghaetest.controller;

import com.java8.java8hanghaetest.dto.RestaurantDto;
import com.java8.java8hanghaetest.dto.RestaurantFoodDto;
import com.java8.java8hanghaetest.model.Food;
import com.java8.java8hanghaetest.model.Restaurant;
import com.java8.java8hanghaetest.model.UserRoleEnum;
import com.java8.java8hanghaetest.security.UserDetailsImpl;
import com.java8.java8hanghaetest.service.RestaurantService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class RestaurantController {

    private final RestaurantService restaurantService;

    // 음식점 등록
//    @Secured(value = UserRoleEnum.Authority.ADMIN)
    @PostMapping("/restaurant/register")
    public Restaurant enrollRestaurant(
        @RequestBody RestaurantDto restaurantDto) {
        return restaurantService.enroll(restaurantDto);
    }

    // 등록된 음식점 모두 조회
//    @Secured(value = UserRoleEnum.Authority.USER)
    @GetMapping("/restaurants")
    public List<Restaurant> getAllRestaurant(
        @RequestParam(required = false) Long x,
        @RequestParam(required = false) Long y) {
        if (x == null && y == null) {
            return restaurantService.getAllRestaurant();
        }
        return restaurantService.getTargetRestaurant(x, y);
    }

    // 음식점 정지
//    @Secured(value = UserRoleEnum.Authority.ADMIN)
    @PutMapping("/restaurant/{restaurantId}/closed")
    public Restaurant closeRestaurant(
        @PathVariable Long restaurantId
    ) {
        // transactional도 안써주고, save도 안했으니까, 상태가 변경되지 않을 것임
        return restaurantService.close(restaurantId);
    }

    // 음식점 영업 시작
//    @Secured(value = UserRoleEnum.Authority.ADMIN)
    @PutMapping("/restaurant/{restaurantId}/open")
    public Restaurant openRestaurant(
        @PathVariable Long restaurantId) {
        // transactional도 안써주고, save도 안했으니까, 상태가 변경되지 않을 것임
        return restaurantService.open(restaurantId);

    }
}
