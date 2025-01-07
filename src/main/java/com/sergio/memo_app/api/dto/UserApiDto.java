package com.sergio.memo_app.api.dto;

import lombok.Builder;

@Builder(toBuilder = true)
public record UserApiDto(Integer id,
                         String username,
                         String email) {
}
