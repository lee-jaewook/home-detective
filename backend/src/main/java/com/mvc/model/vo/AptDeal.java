package com.mvc.model.vo;

import io.swagger.annotations.ApiModel;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@ApiModel(value = "AptDeal (아파트 거래 정보)", description = "아파트 코드와 거래 정보들을 가지고 있는 Domain Class")
public class AptDeal {
    private String aptCode, dealAmount, dealYear, dealMonth, dealDay, area, floor;
}
