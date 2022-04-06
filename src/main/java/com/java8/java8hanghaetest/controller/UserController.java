package com.java8.java8hanghaetest.controller;

import com.java8.java8hanghaetest.dto.SignupRequestDto;
import com.java8.java8hanghaetest.model.User;
import com.java8.java8hanghaetest.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 회원 가입 요청 처리
    @PostMapping("/user/signup")
    public User registerUser(@RequestBody SignupRequestDto requestDto) {
        return userService.registerUser(requestDto);
    }
}
