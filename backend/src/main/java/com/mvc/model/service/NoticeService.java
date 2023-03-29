package com.mvc.model.service;

import com.mvc.model.vo.Notice;

import java.sql.SQLException;
import java.util.List;

public interface NoticeService {
    boolean addNotice(Notice notice) throws SQLException;
    boolean modifyNotice(Notice notice) throws SQLException;
    boolean deleteNotice(String id) throws SQLException;
    List<Notice> getAllNoticeList() throws SQLException;
    Notice getOneNotice(String id) throws SQLException;
}
