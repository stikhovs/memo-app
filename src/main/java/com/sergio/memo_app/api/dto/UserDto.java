package com.sergio.memo_app.api.dto;

import lombok.Builder;

@Builder(toBuilder = true)
public record UserDto(Integer id,
                      String username,
                      String email) {

}
