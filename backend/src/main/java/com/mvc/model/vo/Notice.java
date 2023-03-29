package com.mvc.model.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@ApiModel(value = "Notice (공지사항)", description = "글 번호, 제목, 내용, 작성자, 생성일, 조회수를 가진 Domain Class")
public class Notice {
    private String id;
    private String title;
    private String content;
    private String userId;
    private String regTime;
    private String viewCount;
}
