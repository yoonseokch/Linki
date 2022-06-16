package com.linki.linki.member.exception;

import java.util.NoSuchElementException;

public class WrongPasswordException extends RuntimeException {

    public WrongPasswordException(String message) {
        super(message);
    }
}
