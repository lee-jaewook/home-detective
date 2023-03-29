package com.mvc.model.vo;

import io.swagger.annotations.ApiModel;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@ToString
@RequiredArgsConstructor
@ApiModel(value = "Shop (주변 매장)", description = "번호, 이름, 업종, 주소, 위치를 가진 Domain Class")
public class Shop {
	private String id;
	private String name;
	private String type; // 업종 구분
	private String address;
	private String lat;
	private String lng;
}
