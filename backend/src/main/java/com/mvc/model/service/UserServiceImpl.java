package com.mvc.model.service;

import com.mvc.model.mapper.UserMapper;
import com.mvc.model.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    UserMapper mapper;

    @Autowired
    public UserServiceImpl(UserMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public boolean signUp(User user) throws Exception {
        return mapper.signUp(user);
    }

    @Override
    public User signIn(User user) throws Exception {
        return mapper.signIn(user);
    }

    @Override
    public boolean checkPassword(Map map) throws Exception {
        return mapper.checkPassword(map);
    }

    @Override
    public boolean changePassword(Map map) throws Exception {
        if(mapper.checkPassword(map)){
            return mapper.changePassword(map);
        }
        return false;
    }

    @Override
    public User userInfo(String id) throws Exception {
        return mapper.userInfo(id);
    }

    @Override
    public boolean updateUserInfo(User user) throws Exception {
        return mapper.updateUserInfo(user);
    }

    @Override
    public void saveRefreshToken(Map map) throws Exception {
        mapper.saveRefreshToken(map);
    }

    @Override
    public Object getRefreshToken(String id) throws Exception {
        return mapper.getRefreshToken(id);
    }

    @Override
    public void delRefreshToken(String id) throws Exception {
        mapper.delRefreshToken(id);
    }
}
