package com.mvc.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@ToString
@RequiredArgsConstructor
@ApiModel(value = "School (학교 정보)", description = "이름, 초중고구분, 주소, 위치를 가진 Domain Class")
public class School {
    @ApiModelProperty(value = "학교 이름")
    private String name;
    @ApiModelProperty(value = "초중고 구분")
    private String level;
    @ApiModelProperty(value = "설립일")
    private String estDate;
    @ApiModelProperty(value = "시도")
    private String sidoName;
    @ApiModelProperty(value = "구군")
    private String gugunName;
    @ApiModelProperty(value = "동")
    private String dongName;
    @ApiModelProperty(value = "지번")
    private String jibun;
    @ApiModelProperty(value = "위도")
    private String lat;
    @ApiModelProperty(value = "경도")
    private String lng;
}
