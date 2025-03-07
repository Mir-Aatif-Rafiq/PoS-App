package com.pos.app.service;

import com.pos.app.dao.UserDao;
import com.pos.app.exception.ApiException;
import com.pos.app.pojo.UserPojo;
import com.pos.app.util.EmailChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional(rollbackOn = Exception.class)
public class UserSignUpService {
    @Autowired
    private UserDao userDao;

    public void insertUser(UserPojo userPojo) throws ApiException {
        if (userPojo == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        
        if (userPojo.getEmail() == null || userPojo.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be empty");
        }
        
        if (userPojo.getPassword() == null || userPojo.getPassword().trim().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be empty");
        }
        
        UserPojo existingUser = userDao.select(userPojo.getEmail());
        if (existingUser != null) {
            throw new ApiException("User with given email already exists");
        }
        
        if (EmailChecker.checkAdminEmail(userPojo.getEmail())) {
            userPojo.setRole("admin");
        }
        
        userDao.insert(userPojo);
    }
    
    public UserPojo getUserById(int userId) {
        return userDao.select(userId);
    }
    
    public UserPojo getUserByEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be empty");
        }
        
        return userDao.select(email);
    }

    public List<UserPojo> getAllUsers() {
        return userDao.selectAll();
    }
    
    public void updateUserPassword(String email, String newPassword) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be empty");
        }
        
        if (newPassword == null || newPassword.trim().isEmpty()) {
            throw new IllegalArgumentException("New password cannot be empty");
        }
        
        UserPojo userPojo = userDao.select(email);
        if (userPojo == null) {
            throw new IllegalArgumentException("User not found with email: " + email);
        }
        
        userPojo.setPassword(newPassword);
    }
}
