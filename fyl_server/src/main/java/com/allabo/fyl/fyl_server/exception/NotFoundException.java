package com.allabo.fyl.fyl_server.exception;

public class NotFoundException extends RuntimeException{
    public NotFoundException() {
        super("존재하지 않는 사용자입니다.");
    }
}
