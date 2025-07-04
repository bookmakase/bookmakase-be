package com.bookmakase.exception.auth;

public class DuplicateUsernameException extends RuntimeException {


    public DuplicateUsernameException() {
        super("이미 존재하는 이메일입니다.");
    }

    public DuplicateUsernameException(final String message) {
        super(message);
    }
}
