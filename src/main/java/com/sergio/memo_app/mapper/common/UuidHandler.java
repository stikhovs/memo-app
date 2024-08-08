package com.sergio.memo_app.mapper.common;

import org.mapstruct.Named;

import java.util.UUID;

public interface UuidHandler {

    @Named("randomUuid")
    default UUID random(Object anything) {
        return UUID.randomUUID();
    }

}
