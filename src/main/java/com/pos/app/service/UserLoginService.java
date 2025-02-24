package com.pos.app.service;

import com.pos.app.dao.UserDao;
import com.pos.app.pojo.UserPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Objects;

@Service
public class UserLoginService {
    @Autowired
    private UserDao ud;

    @Transactional
    public UserPojo get(String email){
        return ud.select(email);
    }
    public boolean validatePassword(UserPojo up , String password){
        return (Objects.equals(up.getPassword(), password));
    }

}
