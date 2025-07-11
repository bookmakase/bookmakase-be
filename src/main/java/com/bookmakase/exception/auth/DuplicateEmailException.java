package com.bookmakase.exception.auth;

public class DuplicateEmailException extends RuntimeException {


    public DuplicateEmailException() {
        super("이미 존재하는 이메일입니다.");
    }

    public DuplicateEmailException(final String message) {
        super(message);
    }
}
