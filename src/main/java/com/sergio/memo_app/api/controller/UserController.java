package com.sergio.memo_app.api.controller;

import com.sergio.memo_app.api.dto.UserDto;
import com.sergio.memo_app.persistence.service.UserPersistenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserPersistenceService userPersistenceService;

    @PostMapping("/user/create")
    public UserDto create(@RequestBody UserDto userDto) {
        return userPersistenceService.create(userDto);
    }

    @GetMapping("/user")
    public UserDto get(@RequestParam String username) {
        return userPersistenceService.findBy(username);
    }


}