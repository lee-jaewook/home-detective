package com.mvc.model.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@ApiModel(value = "QnA", description = "글 번호, 제목, 내용, 생성일, 작성자, 댓글 수, 조회수, 답변한 질문 글 번호(null = 질문글)를 가진 Domain Class")
public class Qna {
    private String id;
    private String title;
    private String content;
    private String regTime;
    @ApiModelProperty(value = "사용자 ID", example = "ssafy")
    private String userId;
    private String countReply;
    private String countView;
    @ApiModelProperty(value = "답변 시 Question ID")
    private String parentId;
}
