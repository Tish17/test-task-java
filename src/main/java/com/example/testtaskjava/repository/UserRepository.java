package com.example.testtaskjava.repository;

import com.example.testtaskjava.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Repository
public class UserRepository {

    private final List<User> users = new ArrayList<>();

    public User findByIp(String ip) {
        for (User user : users) {
            if (user.getIp().equals(ip)) {
                log.info("User with ip '{}' is found", user.getIp());
                return user;
            }
        }
        log.info("User with ip '{}' is not found", ip);
        return null;
    }

    public void save(User user) {
        User userFromDb = findByIp(user.getIp());
        if (userFromDb != null) {
            userFromDb.setStart(user.getStart());
            userFromDb.setCount(user.getCount());
            log.info("User is updated: " + user);
        } else {
            users.add(user);
            log.info("User is added: " + user);
        }
    }
}
