package com.sergio.memo_app.persistence.dto;

import lombok.Builder;

@Builder(toBuilder = true)
public record TelegramUserDto(Integer id,
                              String username,
                              Long telegramUserId,
                              Long telegramChatId) {

}
