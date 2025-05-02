package com.sergio.memo_app.api.controller.telegram;

import com.sergio.memo_app.api.dto.TelegramUserApiDto;
import com.sergio.memo_app.api.service.TelegramUserApiService;
import com.sergio.memo_app.persistence.dto.TelegramUserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class TelegramUserController {

    private final TelegramUserApiService telegramUserApiService;

    @PostMapping("/telegram/user/create")
    public TelegramUserApiDto create(@RequestBody TelegramUserDto userDto) {
        return telegramUserApiService.create(userDto);
    }

    @GetMapping("/telegram/user")
    public TelegramUserApiDto get(@RequestParam Long telegramUserId) {
        return telegramUserApiService.get(telegramUserId)
                .orElse(null);
    }

}