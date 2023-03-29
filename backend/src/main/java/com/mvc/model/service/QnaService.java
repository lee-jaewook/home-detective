package com.mvc.model.service;

import com.mvc.model.vo.Notice;
import com.mvc.model.vo.Qna;

import java.sql.SQLException;
import java.util.List;

public interface QnaService {
    boolean addQna(Qna qna) throws SQLException;
    boolean modifyQna(Qna qna) throws SQLException;
    boolean deleteQna(String id) throws SQLException;
    List<Qna> getAllQuestionList(String userId) throws SQLException;
    List<Qna> getAllAnswerList(String id) throws SQLException;
    Qna getOneQna(String id) throws SQLException;
}
