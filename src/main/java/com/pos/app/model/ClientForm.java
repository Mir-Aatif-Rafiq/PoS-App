package com.pos.app.model;

import lombok.Getter;
import lombok.Setter;

import jakarta.validation.constraints.*;

@Getter
@Setter
public class ClientForm {
    @NotBlank(message = "Client name cannot be blank")
    @Size(min = 2, max = 100, message = "Client name must be between 2 and 100 characters")
    private String name;
}
