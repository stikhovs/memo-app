package com.sergio.memo_app.api.service;

import com.sergio.memo_app.api.dto.UserApiDto;
import com.sergio.memo_app.mapper.ApiMapper;
import com.sergio.memo_app.persistence.dto.UserDto;
import com.sergio.memo_app.persistence.service.UserPersistenceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserApiService {

    private final ApiMapper mapper;
    private final UserPersistenceService userPersistenceService;

    public UserApiDto create(UserDto userDto) {
        userPersistenceService.insert(userDto);
        UserDto user = userPersistenceService.findBy(userDto.username());
        return mapper.toUser(user);
    }

    public UserApiDto get(String username) {
        UserDto userDto = userPersistenceService.findBy(username);
        return mapper.toUser(userDto);
    }

}
