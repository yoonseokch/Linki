package com.linki.linki.auth.controller;

import com.linki.linki.auth.dto.LoginRequest;
import com.linki.linki.auth.service.LoginService;
import com.linki.linki.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createAccessToken(@RequestBody @Valid LoginRequest loginRequest){
        loginService.createAccessToken(loginRequest.getLoginID(),loginRequest.getPassword());
        return ResponseEntity.ok().build();
    }
}