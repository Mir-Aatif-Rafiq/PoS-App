package com.pos.app.model;

import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

@Getter
@Setter
public class UserSignUpData extends UserSignUpForm {
    private int id;
    private String role;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;
}
