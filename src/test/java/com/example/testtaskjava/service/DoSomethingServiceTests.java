package com.example.testtaskjava.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootTest
public class DoSomethingServiceTests {

    @Value("${restrict.request.count}")
    private long restrictRequestCount;

    @Autowired
    private DoSomethingService doSomethingService;

    @Test
    public void whenIsOk_thenIsOk() throws InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.invokeAll(List.of(getCallable("1.1.1.1"), getCallable("2.2.2.2")));
    }

    private Callable<Void> getCallable(String remoteAddress) {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRemoteAddr(remoteAddress);
        return () -> {
            for (int i = 0; i < restrictRequestCount; i++) {
                doSomethingService.doSomething(request);
            }
            return null;
        };
    }
}
