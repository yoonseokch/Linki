package com.linki.linki.member.controller;

import com.linki.linki.member.dto.MemberRegisterRequest;
import com.linki.linki.member.service.MemberService;
import com.linki.linki.member.vo.MemberCreateValues;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RestController
@RequiredArgsConstructor
@RequestMapping(value="/member",produces = MediaType.APPLICATION_JSON_VALUE)
public class MemberController {

    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<Void> createMember(@RequestBody @Valid MemberRegisterRequest registerRequest) {
        memberService.addMember(new MemberCreateValues(registerRequest));
        return ResponseEntity.ok().build();
    }
}
