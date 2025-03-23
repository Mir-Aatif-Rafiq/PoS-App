package com.pos.app.dto;

import com.pos.app.exception.ApiException;
import com.pos.app.model.UserLoginData;
import com.pos.app.model.UserLoginForm;
import com.pos.app.pojo.UserPojo;
import com.pos.app.service.UserLoginService;
import com.pos.app.util.SecurityUtil;
import com.pos.app.util.StringUtil;
import com.pos.app.util.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class AuthenticationDto {
    @Autowired
    UserLoginService userLoginService;
    @Autowired
    UserLoginData userLoginData;
    public Map<String, Object> login(HttpServletRequest request, UserLoginForm loginForm){
        if (loginForm == null) {
            throw new IllegalArgumentException("Login Form cannot be null");
        }

        if (loginForm.getEmail() == null || StringUtil.normalize( loginForm.getEmail()).isEmpty()) {
            throw new IllegalArgumentException("Email cannot be empty");
        }

        if (loginForm.getPassword() == null || StringUtil.normalize(loginForm.getPassword()).isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null");
        }
        UserPojo userPojo = userLoginService.getUserByEmail(loginForm.getEmail());
        if (userPojo == null || !userLoginService.validatePassword(userPojo, loginForm.getPassword())) {
            userLoginData.setMessage("Invalid username or password");
            throw new IllegalArgumentException("Invalid username or password");
        }

        Authentication auth = convert(userPojo);

        HttpSession session = request.getSession(true);
//        session.setMaxInactiveInterval(5*60);
        SecurityUtil.createContext(session);
        SecurityUtil.setAuthentication(auth);
        System.out.println("Login Successful");

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Login successful!");

        Map<String, Object> userDetails = new HashMap<>();
        userDetails.put("id", userPojo.getId());
        userDetails.put("email", userPojo.getEmail());
        userDetails.put("name", userPojo.getName());
        userDetails.put("role", userPojo.getRole());

        response.put("user", userDetails);
        return response;
    }

    public Map<String, Object> validateSession(HttpServletRequest request) throws ApiException {
            HttpSession session = request.getSession(false);
            if (session == null) {
                throw new ApiException("No active session found");
            }

            Authentication auth = SecurityUtil.getAuthentication();
            if (auth == null || !auth.isAuthenticated()) {
                throw new ApiException("user not authenticated");
            }

            UserPrincipal principal = (UserPrincipal) auth.getPrincipal();

            Map<String, Object> responseMap = new HashMap<>();
            responseMap.put("valid", true);
            responseMap.put("email", principal.getEmail());
            responseMap.put("userId", principal.getId());

            return responseMap;
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
