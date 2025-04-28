package com.sergio.memo_app.mapper;

import com.sergio.memo_app.generated.tables.*;
import com.sergio.memo_app.persistence.dto.*;
import org.jooq.Record;
import org.jooq.RecordMapper;

public final class PersistenceMapper {

    public static RecordMapper<Record, UserDto> toUserDto() {
        return record -> UserDto.builder()
                .id(record.get(AppUser.APP_USER.ID))
                .username(record.get(AppUser.APP_USER.USERNAME))
                .email(record.get(AppUser.APP_USER.EMAIL))
                .build();
    }

    public static RecordMapper<Record, TelegramUserDto> toTelegramUserDto() {
        return record -> TelegramUserDto.builder()
                .id(record.get(TelegramUser.TELEGRAM_USER.ID))
                .username(record.get(TelegramUser.TELEGRAM_USER.USERNAME))
                .telegramUserId(record.get(TelegramUser.TELEGRAM_USER.TELEGRAM_USER_ID))
                .telegramChatId(record.get(TelegramUser.TELEGRAM_USER.TELEGRAM_CHAT_ID))
                .build();
    }

    public static RecordMapper<Record, CardSetDto> toCardSetDto() {
        return record -> CardSetDto.builder()
                .id(record.get(CardSet.CARD_SET.ID))
                .userId(record.get(CardSet.CARD_SET.USER_ID))
                .categoryId(record.get(CardSet.CARD_SET.CATEGORY_ID))
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

    public static RecordMapper<Record, CategoryDto> toCategoryDto() {
        return record -> CategoryDto.builder()
                .id(record.get(Category.CATEGORY.ID))
                .title(record.get(Category.CATEGORY.TITLE))
                .userId(record.get(Category.CATEGORY.USER_ID))
                .isDefault(record.get(Category.CATEGORY.IS_DEFAULT))
                .build();
    }
}
