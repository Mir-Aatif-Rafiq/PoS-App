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
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;


@Controller
public class AuthenticationController {

    @Autowired
    private UserLoginData uld;
    @Autowired
    private UserLoginService uls;

    @ApiOperation(value = "Log in a user")
    @RequestMapping(path = "/session/login", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<?> login(HttpServletRequest req, UserLoginForm lf) throws ApiException {
        UserPojo up = uls.get(lf.getEmail());

        if(!(up != null && uls.validatePassword(up, lf.getPassword()))) {
            uld.setMessage("Invalid username or password");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }

        Authentication auth = convert(up);

        HttpSession session = req.getSession(true);
        SecurityUtil.createContext(session);
        SecurityUtil.setAuthentication(auth);
        System.out.println("Authentication successful!");

        return ResponseEntity.ok("Login successful!");
    }


//    @ApiOperation(value = "Log in a user")
//    @RequestMapping(path = "/session/login", method = RequestMethod.POST,
//            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
//    public void login(HttpServletRequest req, /*@ModelAttribute*/ UserLoginForm lf) throws ApiException {
//        UserPojo up = uls.get(lf.getEmail());
//        if(!(up != null && uls.validatePassword(up, lf.getPassword()))){
//            uld.setMessage("Invalid username or password");
//            return new ModelAndView("redirect:/app");
//        }
//
//        Authentication auth = convert(up);
//        HttpSession session = req.getSession(true);
//        SecurityUtil.createContext(session);
//        SecurityUtil.setAuthentication(auth);
//        System.out.println("auth done success\n");
//        return new ModelAndView("redirect:/api");
//    }

    @RequestMapping(path = "/session/logout", method = RequestMethod.GET)
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().invalidate();
        return ResponseEntity.ok("Logout successful!");
    }


    private static Authentication convert(UserPojo up) {
        UserPrincipal principal = new UserPrincipal();
        principal.setEmail(up.getEmail());
        principal.setId(up.getId());

        ArrayList<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority(up.getRole()));

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(principal, null,
                authorities);
        return token;
    }
}
