package com.linki.linki.member.vo;

import com.linki.linki.member.dto.MemberRegisterRequest;
import lombok.Getter;

@Getter
public class MemberCreateValues {

    private final String name;
    private final String loginID;
    private final String password;
    private final String phoneNumber;

    public MemberCreateValues(MemberRegisterRequest request) {
        this.name = request.getName();
        this.loginID = request.getLoginID();
        this.password = request.getPassword();
        this.phoneNumber = request.getPhoneNumber();
    }
}
