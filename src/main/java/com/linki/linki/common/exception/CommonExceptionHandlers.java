package com.linki.linki.common.exception;

import com.linki.linki.member.exception.WrongPasswordException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Collections;
import java.util.Map;

@ControllerAdvice
public class CommonExceptionHandlers {

    @ExceptionHandler({WrongPasswordException.class})
    public ResponseEntity<Map<String, String>> unauthorizedHandler(Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap("message", "잘못된 비밀번호입니다."));
    }
}
