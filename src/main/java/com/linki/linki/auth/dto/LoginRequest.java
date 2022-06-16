package com.linki.linki.auth.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
public class LoginRequest {

    @NotBlank
    private final String loginID;

    @NotBlank
    private final String password;
}
