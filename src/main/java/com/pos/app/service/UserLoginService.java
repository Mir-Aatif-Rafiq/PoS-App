package com.pos.app.service;

import com.pos.app.dao.UserDao;
import com.pos.app.pojo.UserPojo;
import com.pos.app.util.PasswordHasher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.transaction.Transactional;

@Service
@Transactional(rollbackOn = Exception.class)
public class UserLoginService {
    @Autowired
    private UserDao userDao;

    public UserPojo getUserByEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be empty");
        }

        return userDao.select(email);
    }

    public boolean validatePassword(UserPojo userPojo, String password) {
        if (userPojo == null) {
            throw new IllegalArgumentException("User cannot be null");
        }

        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be empty");
        }

        return PasswordHasher.isMatch(password, userPojo.getPassword());
    }

}

