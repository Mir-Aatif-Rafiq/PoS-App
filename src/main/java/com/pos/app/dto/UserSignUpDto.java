package com.pos.app.dto;

import com.pos.app.exception.ApiException;
import com.pos.app.model.UserSignUpData;
import com.pos.app.model.UserSignUpForm;
import com.pos.app.pojo.UserPojo;
import com.pos.app.service.UserSignUpService;
import com.pos.app.util.PasswordHasher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserSignUpDto {
    @Autowired
    private UserSignUpService userSignUpService;

    public UserPojo formToPojo(UserSignUpForm userSignUpForm) {
        if (userSignUpForm == null) {
            throw new IllegalArgumentException("User signup form cannot be null");
        }
        
        if (userSignUpForm.getEmail() == null || userSignUpForm.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be empty");
        }
        
        if (userSignUpForm.getName() == null || userSignUpForm.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        
        if (userSignUpForm.getPassword() == null || userSignUpForm.getPassword().trim().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be empty");
        }
        
        UserPojo userPojo = new UserPojo();
        userPojo.setEmail(userSignUpForm.getEmail());
        userPojo.setName(userSignUpForm.getName());
        String hashedPassword = PasswordHasher.passwordHasher(userSignUpForm.getPassword());
        userPojo.setPassword(hashedPassword);

        return userPojo;
    }
    
    public UserSignUpData pojoToData(UserPojo userPojo) {
        if (userPojo == null) {
            throw new IllegalArgumentException("User pojo cannot be null");
        }
        
        UserSignUpData userSignUpData = new UserSignUpData();
        userSignUpData.setEmail(userPojo.getEmail());
        userSignUpData.setName(userPojo.getName());
        userSignUpData.setId(userPojo.getId());
        userSignUpData.setRole(userPojo.getRole());
        userSignUpData.setCreatedAt(userPojo.getCreatedAt());
        userSignUpData.setUpdatedAt(userPojo.getUpdatedAt());
        return userSignUpData;
    }
    
    public void insertUser(UserSignUpForm userSignUpForm) throws ApiException {
        if (userSignUpForm == null) {
            throw new IllegalArgumentException("User signup form cannot be null");
        }
        
        userSignUpService.insertUser(formToPojo(userSignUpForm));
    }
    
    public UserSignUpData getUserById(Integer userId) {
        if (userId <= 0) {
            throw new IllegalArgumentException("User ID must be positive");
        }
        
        UserPojo userPojo = userSignUpService.getUserById(userId);
        if (userPojo == null) {
            throw new IllegalArgumentException("User not found with ID: " + userId);
        }
        
        return pojoToData(userPojo);
    }

    public UserSignUpData getUserByEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be empty");
        }
        
        UserPojo userPojo = userSignUpService.getUserByEmail(email);
        if (userPojo == null) {
            throw new IllegalArgumentException("User not found with email: " + email);
        }
        
        return pojoToData(userPojo);
    }
    
    public List<UserSignUpData> getAllUsers() {
        List<UserPojo> userPojoList = userSignUpService.getAllUsers();
        List<UserSignUpData> userSignUpDataList = new ArrayList<>();
        
        for (UserPojo userPojo : userPojoList) {
            UserSignUpData userSignUpData = pojoToData(userPojo);
            userSignUpDataList.add(userSignUpData);
        }
        
        return userSignUpDataList;
    }
    
    public void updateUserPassword(String email, String newPassword) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be empty");
        }
        
        if (newPassword == null || newPassword.trim().isEmpty()) {
            throw new IllegalArgumentException("New password cannot be empty");
        }
        String hashedPassword = PasswordHasher.passwordHasher(newPassword);
        userSignUpService.updateUserPassword(email, hashedPassword);
    }
}
