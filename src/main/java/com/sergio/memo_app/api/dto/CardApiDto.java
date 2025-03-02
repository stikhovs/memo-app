package com.sergio.memo_app.api.dto;

import lombok.Builder;

@Builder(toBuilder = true)
public record CardApiDto(Long id,
                         String frontSide,
                         String backSide) {
}
