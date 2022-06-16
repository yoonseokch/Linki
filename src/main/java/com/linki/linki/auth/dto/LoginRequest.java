package com.linki.linki.auth.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@RequiredArgsConstructor
public class LoginRequest {

    @NotBlank
    private final String loginID;

    @NotBlank
    private final String password;
}
