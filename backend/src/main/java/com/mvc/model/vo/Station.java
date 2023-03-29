package com.mvc.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@ToString
@RequiredArgsConstructor
@ApiModel(value = "Station (역 정보)", description = "호선, 이름 등 역에 대한 정보를 가진 Domain Class")
public class Station {
    @ApiModelProperty(value = "호선")
    private String lineNum;
    @ApiModelProperty(value = "역이름")
    private String name;
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
