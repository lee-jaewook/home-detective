package com.mvc.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@ApiModel(value = "Like (관심 아파트)", description = "유저의 아이디와 아파트 코드를 가진 Domain Class")
public class Like {

    @ApiModelProperty(example = "ssafy")
    private String userId;
    private String aptCode;
}
