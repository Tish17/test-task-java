package com.example.testtaskjava.service;

import com.example.testtaskjava.annotation.RestrictAccess;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Service
public class DoSomethingService {

    @RestrictAccess
    public String doSomething(HttpServletRequest request) {
        return "";
    }
}
