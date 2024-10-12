package com.allabo.fyl.fyl_server.exception;

import org.springframework.security.core.AuthenticationException;

public class NotFoundException extends AuthenticationException{
    public NotFoundException() {
        super("존재하지 않는 사용자입니다.");
    }
    public NotFoundException(String msg) {
        super(msg);
    }

    public NotFoundException(String msg, Throwable cause) {
        super(msg, cause);
    }
}