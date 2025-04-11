package com.sergio.memo_app.persistence.dto;

import lombok.Builder;

import java.util.List;
import java.util.UUID;

@Builder(toBuilder = true)
public record CardSetDto(Long id,
                         String title,
                         Integer userId,
                         Long categoryId,
                         Long telegramChatId,
                         UUID uuid,
                         List<CardDto> cards) {
}
