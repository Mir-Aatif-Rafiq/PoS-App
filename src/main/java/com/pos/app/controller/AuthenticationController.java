package com.pos.app.controller;

import com.pos.app.dto.AuthenticationDto;
import com.pos.app.exception.ApiException;
import io.swagger.annotations.ApiOperation;
import com.pos.app.model.UserLoginForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
public class AuthenticationController {

    @Autowired
    private AuthenticationDto authenticationDto;

    @ApiOperation(value = "Log in a user")
    @RequestMapping(path = "/session/login", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<?> login(HttpServletRequest request, UserLoginForm loginForm) throws ApiException {
        Map<String, Object> response = authenticationDto.login(request, loginForm);
        return ResponseEntity.ok(response);
    }

    @ApiOperation(value = "Log out a user")
    @RequestMapping(path = "/session/logout", method = RequestMethod.POST)
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {
            request.getSession().invalidate();
            return ResponseEntity.ok("Logout successful!");
    }

    @ApiOperation(value = "Validate user session")
    @RequestMapping(path = "/session/validate", method = RequestMethod.GET)
    public ResponseEntity<?> validateSession(HttpServletRequest request) throws ApiException {
        Map<String, Object> response = authenticationDto.validateSession(request);
        return ResponseEntity.ok(response);
    }
}
