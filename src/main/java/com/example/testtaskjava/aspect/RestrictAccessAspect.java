package com.example.testtaskjava.aspect;

import com.example.testtaskjava.domain.User;
import com.example.testtaskjava.exception.RestrictIpException;
import com.example.testtaskjava.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.time.LocalDateTime;

@Slf4j
@Aspect
@Component
public class RestrictAccessAspect {

    @Value("${restrict.request.count}")
    private long restrictRequestCount;

    @Value("${restrict.request.minute}")
    private long restrictRequestMinute;

    private final UserRepository userRepository;

    public RestrictAccessAspect(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Around("@annotation(com.example.testtaskjava.annotation.RestrictAccess)")
    public Object restrictAccess(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("Restrict access started");
        HttpServletRequest request = (HttpServletRequest) joinPoint.getArgs()[0];
        String ip = request.getRemoteAddr();
        if (ip.equals("0:0:0:0:0:0:0:1")) {
            ip = InetAddress.getLocalHost().getHostAddress();
        }
        User user = userRepository.findByIp(ip);
        if (user != null) {
            checkAccess(user);
        } else {
            user = new User(ip, LocalDateTime.now(), 1L);
        }
        userRepository.save(user);
        log.info("Restrict access ended");
        return joinPoint.proceed();
    }

    private void checkAccess(User user) throws RestrictIpException {
        log.info("Check access started");
        LocalDateTime now = LocalDateTime.now();
        if (user.getCount() >= restrictRequestCount
                && user.getStart().plusMinutes(restrictRequestMinute).isAfter(now)) {
            log.error("Check access ended. Restriction for user: " + user);
            throw new RestrictIpException("Restrict ip address");
        } else if (user.getCount() >= restrictRequestCount
                && user.getStart().plusMinutes(restrictRequestMinute).isBefore(now)) {
            user.setStart(now);
            user.setCount(1L);
            log.info("Restriction lifted for user: " + user);
        } else {
            user.setCount(user.getCount() + 1);
            log.info("Request count is allowed for user: " + user);
        }
        log.info("Check access ended");
    }
}
