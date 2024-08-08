package com.sergio.memo_app.api.dto;

import lombok.Builder;

@Builder(toBuilder = true)
public record CardDto(Long id,
                      String frontSide,
                      String backSide){}
