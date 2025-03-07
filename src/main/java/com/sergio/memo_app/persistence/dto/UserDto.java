package com.sergio.memo_app.persistence.dto;

import lombok.Builder;

@Builder(toBuilder = true)
public record UserDto(Integer id,
                      String username,
                      String email,
                      String telegramUsername,
                      String telegramChatId) {

}
