package com.mvc.model.mapper;

import com.mvc.model.vo.User;
import org.apache.ibatis.annotations.Mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Mapper
public interface UserMapper {
    boolean signUp(User user) throws Exception;
    User signIn(User user) throws Exception;
    boolean updateUserInfo(User user) throws Exception;
    boolean checkPassword(Map map) throws Exception;
    boolean changePassword(Map map) throws Exception;
    User userInfo(String id) throws Exception;
    void saveRefreshToken(Map map) throws Exception;
    Object getRefreshToken(String id) throws Exception;
    void delRefreshToken(String id) throws Exception;

}
