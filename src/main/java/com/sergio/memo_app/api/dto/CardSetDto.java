package com.sergio.memo_app.api.dto;

import lombok.Builder;

import java.util.List;
import java.util.UUID;

@Builder(toBuilder = true)
public record CardSetDto(Long id,
                         String title,
                         Long userId,
                         UUID uuid,
                         List<CardDto> cards) {
}
