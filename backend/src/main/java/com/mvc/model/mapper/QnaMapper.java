package com.mvc.model.mapper;

import com.mvc.model.vo.Notice;
import com.mvc.model.vo.Qna;
import org.apache.ibatis.annotations.Mapper;

import java.sql.SQLException;
import java.util.List;

@Mapper
public interface QnaMapper {
    boolean addQna(Qna qna) throws SQLException;
    boolean modifyQna(Qna qna) throws SQLException;
    boolean deleteQna(String id) throws SQLException;
    List<Qna> getAllQuestionList(String userId) throws SQLException;
    List<Qna> getAllAnswerList(String id) throws SQLException;
    Qna getOneQna(String id) throws SQLException;
    boolean countUpViewCount(String id) throws SQLException;
    boolean countUpReply(String parentId) throws SQLException;
}
