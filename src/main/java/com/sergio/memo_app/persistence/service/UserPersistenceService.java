package com.sergio.memo_app.persistence.service;

import com.sergio.memo_app.api.dto.UserDto;
import com.sergio.memo_app.mapper.UserMapper;
import com.sergio.memo_app.persistence.entity.User;
import com.sergio.memo_app.persistence.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserPersistenceService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;

    public UserDto create(UserDto userDto) {
        User savedUser = userRepository.save(userMapper.toEntity(userDto));
        return userMapper.toDto(savedUser);
    }

    public UserDto findBy(String username) {
       return userRepository.findByUsername(username)
               .map(userMapper::toDto)
               .orElseThrow(() -> new RuntimeException("Couldn't find user by name: %s".formatted(username)));
    }

}
