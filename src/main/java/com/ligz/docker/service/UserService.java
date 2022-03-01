package com.ligz.docker.service;

import com.ligz.docker.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class UserService {

    public void createUser(User user) {
        log.info("create user: {}", user);
    }
}
