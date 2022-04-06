package com.java8.java8hanghaetest.controller;

import com.java8.java8hanghaetest.dto.OptionDto;
import com.java8.java8hanghaetest.model.Food;
import com.java8.java8hanghaetest.service.OptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OptionController {

    private final OptionService optionService;

    @PostMapping("/option")
    public Food addOption(@RequestBody OptionDto optionDto) {
        return optionService.addOption(optionDto);
    }
}
