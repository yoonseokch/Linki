package com.linki.linki.member.service;

import com.linki.linki.member.domain.Member;
import com.linki.linki.member.domain.MemberRole;
import com.linki.linki.member.exception.DuplicateArgumentException;
import com.linki.linki.member.exception.WrongPasswordException;
import com.linki.linki.member.repository.MemberRepository;
import com.linki.linki.member.vo.MemberCreateValues;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public void addMember(MemberCreateValues values) {
        try {
            memberRepository.save(Member.builder()
                    .loginID(values.getLoginID())
                    .passwordHash(passwordEncoder.encode(values.getPassword())) // need to be hashed
                    .phoneNumber(values.getPhoneNumber())
                    .name(values.getName())
                    .memberRole(MemberRole.MEMBER)
                    .build());
        } catch (DataAccessException e) {
            // TODO 논의 필요
            String duplicateValue = getDuplicateValueFromException(e);
            throw new DuplicateArgumentException(duplicateValue + "가 중복된 키입니다.");
        }
    }

    private String getDuplicateValueFromException(DataAccessException e) {
        return e.getCause().getCause().getMessage().split(" ")[2];
    }

    public Member getMemberIfExist(String loginID, String password) {
        return memberRepository.findByLoginID(loginID)
                .filter(member -> passwordEncoder.matches(password, member.getPasswordHash()))
                .orElseThrow(() -> new WrongPasswordException("아이디와 비밀번호를 확인해주세요."));

    }
}
