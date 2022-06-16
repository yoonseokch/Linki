package com.linki.linki.member.dto;

import com.linki.linki.member.dto.validation.Password;
import com.linki.linki.member.dto.validation.Phone;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@RequiredArgsConstructor
public class MemberRegisterRequest {

    @NotBlank(message = "이름은 공백일 수 없습니다.")
    private final String name;

    @NotBlank(message = "로그인 아이디는 공백일 수 없습니다.")
    private final String loginID;

    @Password
    private final String password;

    @NotBlank(message = "패스워드 체크는 공백일 수 없습니다.")
    private final String passwordCheck;

    @Phone
    private final String phoneNumber;

}
