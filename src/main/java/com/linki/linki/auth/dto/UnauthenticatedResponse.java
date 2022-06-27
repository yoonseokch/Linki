package com.linki.linki.auth.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UnauthenticatedResponse {

    private final String message;
}
