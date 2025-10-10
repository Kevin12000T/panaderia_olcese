package com.olcese.panaderia.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {

    @NotBlank
    @Email
    private String email;   // usamos email como identificador del usuario

    @NotBlank
    private String password;
}
