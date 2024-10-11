package com.allabo.fyl.kb_server.assets.exception;

public class NotFoundException extends RuntimeException{
    public NotFoundException(String id) {
        super("존재하지 않는 사용자입니다.");
    }
}
