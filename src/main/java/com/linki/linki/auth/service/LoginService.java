package com.linki.linki.auth.service;

import com.linki.linki.auth.common.JwtTokenProvider;
import com.linki.linki.member.domain.Member;
import com.linki.linki.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final MemberService memberService;
    private final JwtTokenProvider jwtTokenProvider;

    public String createAccessToken(String loginID, String password) {
        Member member = memberService.getMemberIfExist(loginID,password);
        return jwtTokenProvider.createToken(member);
    }
}
