package com.allabo.fyl.fyl_server.advice;

import com.allabo.fyl.fyl_server.exception.AddException;
import com.allabo.fyl.fyl_server.exception.FindException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomerAdvice {

    /**
     * 고객 FindException
     * @param e
     * @return
     */
    @ExceptionHandler(FindException.class)
    public ResponseEntity customerFindExceptionHandler(FindException e){
        HttpHeaders headers = new HttpHeaders();
        headers.add("content-type", "text/html;charset=UTF-8");
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .headers(headers)
                .body(e.getMessage());
    }

    @ExceptionHandler(AddException.class)
    public ResponseEntity customerAddExceptionHandler(AddException e){
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .build();
    }
}
