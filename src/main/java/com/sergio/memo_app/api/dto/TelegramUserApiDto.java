package com.sergio.memo_app.api.dto;

import lombok.Builder;

@Builder(toBuilder = true)
public record TelegramUserApiDto(Integer id,
                                 String username,
                                 Long telegramUserId,
                                 Long telegramChatId) {
}
