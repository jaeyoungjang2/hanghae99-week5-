package com.java8.java8hanghaetest.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OptionDto {

    private Long foodId;
    // 옵션 이름
    private String name;
    // 옵션 가격
    private Long price;
}
