package com.mvc.model.service;

import com.mvc.model.mapper.NoticeMapper;
import com.mvc.model.vo.Notice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class NoticeServiceImpl implements NoticeService{

    NoticeMapper mapper;

    @Autowired
    public NoticeServiceImpl(NoticeMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public boolean addNotice(Notice notice) throws SQLException {
        return mapper.addNotice(notice);
    }

    @Override
    public boolean modifyNotice(Notice notice) throws SQLException {
        return mapper.modifyNotice(notice);
    }

    @Override
    public boolean deleteNotice(String id) throws SQLException {
        return mapper.deleteNotice(id);
    }

    @Override
    public List<Notice> getAllNoticeList() throws SQLException {
        return mapper.getAllNoticeList();
    }

    @Override
    public Notice getOneNotice(String id) throws SQLException {
        mapper.countUpViewCount(id);
        return mapper.getOneNotice(id);
    }
}
