package com.example.testtaskjava.controller;

import com.example.testtaskjava.service.DoSomethingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class DoSomethingController {

    private final DoSomethingService doSomethingService;

    public DoSomethingController(DoSomethingService doSomethingService) {
        this.doSomethingService = doSomethingService;
    }

    @GetMapping("/")
    public ResponseEntity<String> doSomething(HttpServletRequest request) {
        return new ResponseEntity<>(doSomethingService.doSomething(request), HttpStatus.OK);
    }
}
