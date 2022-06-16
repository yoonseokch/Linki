package com.linki.linki.auth.dto;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class UnauthenticatedException extends RuntimeException{

    public UnauthenticatedException(String message) {
        super(message);
    }

}
