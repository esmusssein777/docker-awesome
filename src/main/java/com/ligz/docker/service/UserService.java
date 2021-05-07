package com.ligz.docker.service;

import com.ligz.docker.entity.User;
import com.ligz.docker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class UserService {
    private final UserRepository userRepository;

    public Long createUser(User user) {
        log.info("create user: {}", user);
        User result = userRepository.save(user);
        return result.getId();
    }
}
