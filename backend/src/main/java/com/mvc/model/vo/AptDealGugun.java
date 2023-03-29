package com.mvc.model.vo;

import io.swagger.annotations.ApiModel;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@ApiModel(value = "AptDealCount (월별 아파트 거래수 정보)", description = "년,월, 거래량, 거래금액 Domain Class")
public class AptDealGugun {
    private String sidoName, gugunName, deal;
}
