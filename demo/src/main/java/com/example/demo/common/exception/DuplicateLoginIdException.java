package com.example.demo.common.exception;

public class DuplicateLoginIdException extends RuntimeException {

    public DuplicateLoginIdException() {
        super("이미 사용 중인 아이디입니다.");
    }
}
