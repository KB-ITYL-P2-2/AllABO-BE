package com.allabo.fyl.fyl_server.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class FindException extends RuntimeException { // 예외를 RuntimeException으로 변경
    public FindException(String message) {
        super(message);
    }

    public FindException() {
    }
}