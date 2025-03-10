package com.pos.app.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
@Getter
@Setter
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserLoginData implements Serializable {
    private static final long serialVersionUID = 1L;

    private String message;
    private String email;

    public UserLoginData() {
        message = "No message";
        email = "No email";
    }
}