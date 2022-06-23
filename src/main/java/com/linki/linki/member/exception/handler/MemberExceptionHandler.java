package com.linki.linki.member.exception.handler;

import com.linki.linki.member.exception.DuplicateArgumentException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Collections;
import java.util.Map;

@ControllerAdvice
public class MemberExceptionHandler {

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(DuplicateArgumentException.class)
    @ResponseBody
    public Map<String, String> wrongPasswordHandler(Exception e) {
        return Collections.singletonMap("message", e.getMessage());
    }
}
