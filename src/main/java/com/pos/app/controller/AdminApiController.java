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
import org.springframework.security.access.method.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api
@RestController
public class AdminApiController {

    @Autowired
    private UserSignUpDto userSignUpDto;

    @ApiOperation(value = "Add a new user")
    @RequestMapping(path = "/session/signup", method = RequestMethod.POST)
    public ResponseEntity<?> addUser(@RequestBody UserSignUpForm userSignUpForm) throws ApiException {
            userSignUpDto.insertUser(userSignUpForm);
            return ResponseEntity.status(HttpStatus.CREATED).body("User created successfully");
    }

    @ApiOperation(value = "Get all users")
    @RequestMapping(path = "/api/admin/user", method = RequestMethod.GET)
    public ResponseEntity<?> getAllUsers() {
            List<UserSignUpData> userSignUpDataList = userSignUpDto.getAllUsers();
            return ResponseEntity.ok(userSignUpDataList);
    }
    
    @ApiOperation(value = "Get user by ID")
    @RequestMapping(path = "/api/admin/user/{userId}", method = RequestMethod.GET)
    public ResponseEntity<?> getUserById(@PathVariable Integer userId) throws ApiException {
            UserSignUpData userSignUpData = userSignUpDto.getUserById(userId);
            return ResponseEntity.ok(userSignUpData);
    }
    
    @ApiOperation(value = "Get user by email")
    @RequestMapping(path = "/api/admin/user/email/{email}", method = RequestMethod.GET)
    public ResponseEntity<?> getUserByEmail(@PathVariable String email) throws ApiException {
            UserSignUpData userSignUpData = userSignUpDto.getUserByEmail(email);
            return ResponseEntity.ok(userSignUpData);
    }
}

