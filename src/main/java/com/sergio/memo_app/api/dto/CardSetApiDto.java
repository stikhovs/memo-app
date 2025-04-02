package com.sergio.memo_app.api.dto;

import lombok.Builder;

import java.util.List;
import java.util.UUID;

@Builder(toBuilder = true)
public record CardSetApiDto(Long id,
                            String title,
                            Integer userId,
                            UUID uuid,
                            List<CardApiDto> cards) {
}
