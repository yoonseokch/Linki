package com.linki.linki.member.dto.validation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PhoneValidatorTest {

    private final PhoneValidator validator;

    public PhoneValidatorTest() {
        this.validator = new PhoneValidator();
    }

    @Test
    public void 올바른_전화번호(){
        final String validPhoneNumber = "010-1234-5678";
        assertTrue(validator.isValid(validPhoneNumber,null));
    }

    @Test
    public void 틀린_전화번호1(){
        final String validPhoneNumber = "010-12345-5678";
        assertFalse(validator.isValid(validPhoneNumber,null));
    }

    @Test
    public void 틀린_전화번호2(){
        final String validPhoneNumber = "010-123-5678";
        assertFalse(validator.isValid(validPhoneNumber,null));
    }

    @Test
    public void 틀린_전화번호3(){
        final String validPhoneNumber = "01012345678";
        assertFalse(validator.isValid(validPhoneNumber,null));
    }
}