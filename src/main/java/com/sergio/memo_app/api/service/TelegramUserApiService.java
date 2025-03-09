package com.sergio.memo_app.api.service;

import com.sergio.memo_app.api.dto.TelegramUserApiDto;
import com.sergio.memo_app.mapper.ApiMapper;
import com.sergio.memo_app.persistence.dto.TelegramUserDto;
import com.sergio.memo_app.persistence.service.TelegramUserPersistenceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TelegramUserApiService {

    private final ApiMapper mapper;
    private final TelegramUserPersistenceService telegramUserPersistenceService;

    public TelegramUserApiDto create(TelegramUserDto userDto) {
        return mapper.toTelegramUser(telegramUserPersistenceService.insert(userDto));
    }

    public Optional<TelegramUserApiDto> get(Long telegramUserId) {
        return telegramUserPersistenceService.findByTelegramUserId(telegramUserId)
                .map(mapper::toTelegramUser);
    }
}
