package com.linki.linki.member.dto.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordValidator implements ConstraintValidator<Password,String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        // TODO 패스워드 패턴 변경시 변경필요
        // TODO 패스워드 체크할 건지 비지니스 로직 검증 필요
        Pattern pattern = Pattern.compile("[a-zA-Z_0-9]{10,}");
        Matcher matcher = pattern.matcher(value);
        return matcher.matches();
    }
}
