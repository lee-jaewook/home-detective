package com.mvc.model.service;

import com.mvc.model.vo.User;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface UserService {
    boolean signUp(User user) throws Exception;
    User signIn(User user) throws Exception;
    boolean checkPassword(Map map) throws Exception;
    boolean changePassword(Map map) throws Exception;
    User userInfo(String id) throws Exception;
    boolean updateUserInfo(User user) throws Exception;
    void saveRefreshToken(Map map) throws Exception;
    Object getRefreshToken(String id) throws Exception;
    void delRefreshToken(String id) throws Exception;
}
