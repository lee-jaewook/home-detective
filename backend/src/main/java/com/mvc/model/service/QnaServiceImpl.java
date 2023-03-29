package com.mvc.model.service;

import com.mvc.model.mapper.NoticeMapper;
import com.mvc.model.mapper.QnaMapper;
import com.mvc.model.vo.Notice;
import com.mvc.model.vo.Qna;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class QnaServiceImpl implements QnaService{

    QnaMapper mapper;

    @Autowired
    public QnaServiceImpl(QnaMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public boolean addQna(Qna qna) throws SQLException {
        if(qna.getParentId() != null){
            mapper.countUpReply(qna.getParentId());
        }
        return mapper.addQna(qna);
    }

    @Override
    public boolean modifyQna(Qna qna) throws SQLException {
        return mapper.modifyQna(qna);
    }

    @Override
    public boolean deleteQna(String id) throws SQLException {
        return mapper.deleteQna(id);
    }

    @Override
    public List<Qna> getAllQuestionList(String userId) throws SQLException {
        return mapper.getAllQuestionList(userId);
    }

    @Override
    public List<Qna> getAllAnswerList(String id) throws SQLException {
        return mapper.getAllAnswerList(id);
    }

    @Override
    public Qna getOneQna(String id) throws SQLException {
        mapper.countUpViewCount(id);
        return mapper.getOneQna(id);
    }
}
