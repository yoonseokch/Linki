package com.linki.linki.member.service;

import com.linki.linki.member.domain.Member;
import com.linki.linki.member.domain.MemberRole;
import com.linki.linki.member.exception.WrongPasswordException;
import com.linki.linki.member.repository.MemberRepository;
import com.linki.linki.member.vo.MemberCreateValues;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public void addMember(MemberCreateValues values){
        memberRepository.save(Member.builder()
                .loginID(values.getLoginID())
                .passwordHash(passwordEncoder.encode(values.getPassword())) // need to be hashed
                .phoneNumber(values.getPhoneNumber())
                .name(values.getName())
                .memberRole(MemberRole.MEMBER)
                .build());
    }

    public Member getMemberIfExist(String loginID, String password) {
        return memberRepository.findByLoginIDandPasswordHash(loginID,passwordEncoder.encode(password))
                .orElseThrow(() -> new WrongPasswordException("아이디와 비밀번호를 확인해주세요."));

    }
}
