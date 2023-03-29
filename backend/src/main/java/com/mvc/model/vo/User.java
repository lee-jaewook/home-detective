package com.mvc.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@ToString
@NoArgsConstructor
@ApiModel(value = "User (사용자)", description = "id, pass, name, phone, email를 가진 Domain Class")
public class User {
    @ApiModelProperty(value = "아이디", example = "noori")
    private String id;
    @ApiModelProperty(value = "비밀번호", example = "1234")
    private String pass;
    @ApiModelProperty(value = "이름", example = "노누리")
    private String name;
    @ApiModelProperty(value = "핸드폰 번호", example = "010-1234-5678")
    private String phone;
    @ApiModelProperty(value = "이메일 주소", example = "example@naver.com")
    private String email;
    @ApiModelProperty(value = "우편번호", example = "18123")
    private String postCode;
    @ApiModelProperty(value = "주소", example = "울산광역시 남구 신정동")
    private String address;
    @ApiModelProperty(value = "상세 주소", example = "울산로 10번길 ")
    private String detailAddress;
    @ApiModelProperty(value = "역할")
    private String role;
    @ApiModelProperty(value = "소셜 로그인 여부 default : HOME")
    private String social;

    public User(String id, String name, String phone, String email, String address, String social) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.social = social;
    }
}
