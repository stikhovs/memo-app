package com.sergio.memo_app.mapper;

import com.sergio.memo_app.api.dto.CardDto;
import com.sergio.memo_app.api.dto.CardSetDto;
import com.sergio.memo_app.api.dto.UserDto;
import com.sergio.memo_app.generated.tables.AppUser;
import com.sergio.memo_app.generated.tables.Card;
import com.sergio.memo_app.generated.tables.CardSet;
import org.jooq.Record;
import org.jooq.RecordMapper;

public final class Mapper {

    public static RecordMapper<Record, UserDto> toUserDto() {
        return record -> UserDto.builder()
                .id(record.get(AppUser.APP_USER.ID))
                .username(record.get(AppUser.APP_USER.USERNAME))
                .email(record.get(AppUser.APP_USER.EMAIL))
                .build();
    }

    public static RecordMapper<Record, CardSetDto> toCardSetDto() {
        return record -> CardSetDto.builder()
                .id(record.get(CardSet.CARD_SET.ID))
                .userId(record.get(CardSet.CARD_SET.USER_ID).longValue())
                .uuid(record.get(CardSet.CARD_SET.UUID))
                .title(record.get(CardSet.CARD_SET.TITLE))
                .build();
    }

    public static RecordMapper<Record, CardDto> toCardDto() {
        return record -> CardDto.builder()
                .id(record.get(Card.CARD.ID))
                .frontSide(record.get(Card.CARD.FRONT_SIDE))
                .backSide(record.get(Card.CARD.BACK_SIDE))
                .build();
    }
}
