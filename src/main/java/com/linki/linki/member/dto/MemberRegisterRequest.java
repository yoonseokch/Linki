package com.linki.linki.member.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
public class MemberRegisterRequest {

    @NotBlank(message = "이름은 공백일 수 없습니다.")
    private final String name;

    @NotBlank(message = "로그인 아이디는 공백일 수 없습니다.")
    private final String loginID;

    @NotBlank(message = "비밀번호는 공백일 수 없습니다.")
    @Pattern(regexp = "[a-zA-Z_0-9]{8,16}", message = "비밀번호는 8~16자 영문 대 소문자, 숫자를 사용하세요.")
    private final String password;

    @NotBlank
    @Pattern(regexp = "\\d{3}-\\d{4}-\\d{4}", message = "핸드폰 번호가 형식에 맞지 않습니다.")
    private final String phoneNumber;

}
