package com.pos.app.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import com.pos.app.dto.UserSignUpDto;
import com.pos.app.exception.ApiException;
import com.pos.app.model.UserSignUpData;
import com.pos.app.model.UserSignUpForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api
@RestController
public class AdminApiController {

    @Autowired
    private UserSignUpDto userSignUpDto;

    @ApiOperation(value = "Add a new user")
    @RequestMapping(path = "/api/user/signup", method = RequestMethod.POST)
    public ResponseEntity<?> addUser(@RequestBody UserSignUpForm userSignUpForm) {
        try {
            if (userSignUpForm == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User form cannot be null");
            }
            
            userSignUpDto.insertUser(userSignUpForm);
            return ResponseEntity.status(HttpStatus.CREATED).body("User created successfully");
        } catch (ApiException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating user: " + e.getMessage());
        }
    }

    @ApiOperation(value = "Get all users")
    @RequestMapping(path = "/api/user", method = RequestMethod.GET)
    public ResponseEntity<?> getAllUsers() {
        try {
            List<UserSignUpData> userSignUpDataList = userSignUpDto.getAllUsers();
            return ResponseEntity.ok(userSignUpDataList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error retrieving users: " + e.getMessage());
        }
    }
    
    @ApiOperation(value = "Get user by ID")
    @RequestMapping(path = "/api/user/{userId}", method = RequestMethod.GET)
    public ResponseEntity<?> getUserById(@PathVariable Integer userId) {
        try {
            if (userId <= 0) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User ID must be positive");
            }
            
            UserSignUpData userSignUpData = userSignUpDto.getUserById(userId);
            return ResponseEntity.ok(userSignUpData);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error retrieving user: " + e.getMessage());
        }
    }
    
    @ApiOperation(value = "Get user by email")
    @RequestMapping(path = "/api/user/email/{email}", method = RequestMethod.GET)
    public ResponseEntity<?> getUserByEmail(@PathVariable String email) {
        try {
            if (email == null || email.trim().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email cannot be empty");
            }
            
            UserSignUpData userSignUpData = userSignUpDto.getUserByEmail(email);
            return ResponseEntity.ok(userSignUpData);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error retrieving user: " + e.getMessage());
        }
    }
    
    @ApiOperation(value = "Update user password")
    @RequestMapping(path = "/api/user/password/{email}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateUserPassword(@PathVariable String email, @RequestBody String newPassword) {
        try {
            if (email == null || email.trim().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email cannot be empty");
            }
            
            if (newPassword == null || newPassword.trim().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("New password cannot be empty");
            }
            
            userSignUpDto.updateUserPassword(email, newPassword);
            return ResponseEntity.ok("Password updated successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error updating password: " + e.getMessage());
        }
    }
}

