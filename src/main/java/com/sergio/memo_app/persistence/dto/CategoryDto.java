package com.sergio.memo_app.persistence.dto;

import lombok.Builder;

@Builder(toBuilder = true)
public record CategoryDto (
        Long id,
        String title,
        Integer userId

) {}
