package com.mvc.model.vo;

import io.swagger.annotations.ApiModel;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@ApiModel(value = "Address (주소 정보)", description = "시-구군의 위도 경도를 가진 Domain Class")
public class Address {
    private String sidoName, gugunName, lat, lng;
}
