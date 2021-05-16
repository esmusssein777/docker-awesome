package com.ligz.docker.controller;

import com.ligz.docker.entity.User;
import com.ligz.docker.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
@Log4j2
public class UserController {
    private final UserService userService;

    @PostMapping
    public void createUser(User user) {
        Long id = userService.createUser(user);
        log.info("create user success, id = {}", id);
    }
}
