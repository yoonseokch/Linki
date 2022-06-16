package com.linki.linki.common.exception;

import com.linki.linki.auth.dto.UnauthenticatedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Collections;
import java.util.Map;

@ControllerAdvice
public class CommonExceptionHandlers {

    @ExceptionHandler({UnauthenticatedException.class})
    public ResponseEntity<Map<String, String>> unauthorizedHandler(Exception e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Collections.singletonMap("message", e.getMessage()));
    }
}
