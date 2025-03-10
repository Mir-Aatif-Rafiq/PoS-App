package com.pos.app.controller;

import com.pos.app.exception.ApiException;
import io.swagger.annotations.ApiOperation;
import com.pos.app.util.UserPrincipal;
import com.pos.app.model.UserLoginData;
import com.pos.app.model.UserLoginForm;
import com.pos.app.pojo.UserPojo;
import com.pos.app.service.UserLoginService;
import com.pos.app.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;

@Controller
public class AuthenticationController {

    @Autowired
    private UserLoginData userLoginData;
    
    @Autowired
    private UserLoginService userLoginService;

    @ApiOperation(value = "Log in a user")
    @RequestMapping(path = "/session/login", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<?> login(HttpServletRequest request, UserLoginForm loginForm) throws ApiException {
        try {
            if (loginForm == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Login form cannot be null");
            }
            
            if (loginForm.getEmail() == null || loginForm.getEmail().trim().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email cannot be empty");
            }
            
            if (loginForm.getPassword() == null || loginForm.getPassword().trim().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Password cannot be empty");
            }
            UserPojo userPojo = userLoginService.getUserByEmail(loginForm.getEmail());
            if (userPojo == null || !userLoginService.validatePassword(userPojo, loginForm.getPassword())) {
                userLoginData.setMessage("Invalid username or password");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
            }
            
            Authentication auth = convert(userPojo);
            
            HttpSession session = request.getSession(true);
            session.setMaxInactiveInterval(5*60);
            SecurityUtil.createContext(session);
            SecurityUtil.setAuthentication(auth);
            
            return ResponseEntity.ok("Login successful!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error during login: " + e.getMessage());
        }
    }

    @ApiOperation(value = "Log out a user")
    @RequestMapping(path = "/session/logout", method = RequestMethod.GET)
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.getSession().invalidate();
            return ResponseEntity.ok("Logout successful!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error during logout: " + e.getMessage());
        }
    }

    private static Authentication convert(UserPojo userPojo) {
        if (userPojo == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        
        UserPrincipal principal = new UserPrincipal();
        principal.setEmail(userPojo.getEmail());
        principal.setId(userPojo.getId());

        ArrayList<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(userPojo.getRole()));

        return new UsernamePasswordAuthenticationToken(principal, null, authorities);
    }
}
