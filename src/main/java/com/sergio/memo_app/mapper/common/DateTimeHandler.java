package com.sergio.memo_app.mapper.common;

import org.mapstruct.Named;

import java.time.LocalDateTime;

public interface DateTimeHandler<T> {
    @Named("now")
    default LocalDateTime now(T anything) {
        return LocalDateTime.now();
    }
}
