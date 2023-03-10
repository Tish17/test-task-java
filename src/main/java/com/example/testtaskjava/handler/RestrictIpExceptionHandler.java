package com.example.testtaskjava.handler;

import com.example.testtaskjava.exception.RestrictIpException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestrictIpExceptionHandler {

    @ExceptionHandler(RestrictIpException.class)
    public ResponseEntity<String> restrictIpExceptionHandler() {
        return new ResponseEntity<>("", HttpStatus.BAD_GATEWAY);
    }
}
