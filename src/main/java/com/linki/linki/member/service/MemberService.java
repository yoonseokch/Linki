package com.linki.linki.member.service;

import com.linki.linki.member.domain.Member;
import com.linki.linki.member.domain.MemberRole;
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
}