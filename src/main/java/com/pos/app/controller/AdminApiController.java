package com.pos.app.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import com.pos.app.dto.UserSignUpDto;
import com.pos.app.exception.ApiException;
import com.pos.app.model.UserSignUpData;
import com.pos.app.model.UserSignUpForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api
@RestController
public class AdminApiController {

    @Autowired
    private UserSignUpDto usd;

    @ApiOperation(value = "Adds a user")
    @RequestMapping(path = "/api/user", method = RequestMethod.POST)
    public void addUser(@RequestBody UserSignUpForm form) throws ApiException {
        usd.insert(form);
    }

    @ApiOperation(value = "Deletes a user")
    @RequestMapping(path = "/api/user/{id}", method = RequestMethod.DELETE)
    public void deleteUser(@PathVariable int id) {
        usd.delete(id);
    }

    @ApiOperation(value = "Gets list of all users")
    @RequestMapping(path = "/api/user", method = RequestMethod.GET)
    public List<UserSignUpData> getAllUser() {
        List<UserSignUpData> list = usd.getAll();
        return list;
    }

}

