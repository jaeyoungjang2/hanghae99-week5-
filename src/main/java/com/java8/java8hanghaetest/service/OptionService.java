package com.java8.java8hanghaetest.service;

import com.java8.java8hanghaetest.dto.OptionDto;
import com.java8.java8hanghaetest.model.Food;
import com.java8.java8hanghaetest.model.Option;
import com.java8.java8hanghaetest.repository.FoodRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OptionService {

    private final FoodRepository foodRepository;

    public Food addOption(OptionDto optionDto) {
        String optionName = optionDto.getName();
        Long optionPrice = optionDto.getPrice();
        Long optionFoodId = optionDto.getFoodId();

        Food food = foodRepository.findById(optionFoodId).orElseThrow(
            () -> new IllegalArgumentException("음식이 존재하지 않아 옵션을 추가할 수 없습니다.")
        );

        Option option = new Option(food, optionName, optionPrice);
        food.addOption(option);

        return food;
    }
}
