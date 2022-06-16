package com.linki.linki.member.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.linki.linki.member.dto.validation.Password;
import com.linki.linki.member.dto.validation.Phone;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
public class MemberRegisterRequest {

    @NotBlank(message = "이름은 공백일 수 없습니다.")
    private String name;

    @NotBlank(message = "로그인 아이디는 공백일 수 없습니다.")
    private String loginID;

    @Password
    private String password;

    @NotBlank(message = "패스워드 체크는 공백일 수 없습니다.")
    private String passwordCheck;

    @Phone
    private String phoneNumber;

    public MemberRegisterRequest(@NotBlank(message = "이름은 공백일 수 없습니다.") String name, @NotBlank(message = "로그인 아이디는 공백일 수 없습니다.") String loginID, String password, @NotBlank(message = "패스워드 체크는 공백일 수 없습니다.") String passwordCheck, String phoneNumber) {
        this.name = name;
        this.loginID = loginID;
        this.password = password;
        this.passwordCheck = passwordCheck;
        this.phoneNumber = phoneNumber;
    }
}
