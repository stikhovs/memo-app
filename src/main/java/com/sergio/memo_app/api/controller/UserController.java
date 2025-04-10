package com.sergio.memo_app.api.controller;

import com.sergio.memo_app.api.dto.UserApiDto;
import com.sergio.memo_app.api.service.UserApiService;
import com.sergio.memo_app.persistence.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserApiService userApiService;

    @PostMapping("/api/user/create")
    public UserApiDto create(@RequestBody UserDto userDto) {
        return userApiService.create(userDto);
    }

    @GetMapping("/api/user")
    public UserApiDto get(@RequestParam String username) {
        return userApiService.get(username);
    }

}