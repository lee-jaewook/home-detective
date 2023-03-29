package com.mvc.model.mapper;

import com.mvc.model.vo.Notice;
import org.apache.ibatis.annotations.Mapper;

import java.sql.SQLException;
import java.util.List;

@Mapper
public interface NoticeMapper {
    boolean addNotice(Notice notice) throws SQLException;
    boolean modifyNotice(Notice notice) throws SQLException;
    boolean deleteNotice(String id) throws SQLException;
    List<Notice> getAllNoticeList() throws SQLException;
    Notice getOneNotice(String id) throws SQLException;
    boolean countUpViewCount(String id) throws SQLException;
}
