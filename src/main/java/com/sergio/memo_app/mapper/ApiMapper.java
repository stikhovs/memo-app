package com.sergio.memo_app.mapper;

import com.sergio.memo_app.api.dto.CardApiDto;
import com.sergio.memo_app.api.dto.CardSetApiDto;
import com.sergio.memo_app.api.dto.TelegramUserApiDto;
import com.sergio.memo_app.api.dto.UserApiDto;
import com.sergio.memo_app.persistence.dto.CardDto;
import com.sergio.memo_app.persistence.dto.CardSetDto;
import com.sergio.memo_app.persistence.dto.TelegramUserDto;
import com.sergio.memo_app.persistence.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ApiMapper {

    CardApiDto toCard(CardDto cardDto);
    List<CardApiDto> toCards(List<CardDto> cards);
    CardSetApiDto toCardSet(CardSetDto cardSetDto);
    List<CardSetApiDto> toCardSets(List<CardSetDto> cardSetDto);
    UserApiDto toUser(UserDto userDto);
    TelegramUserApiDto toTelegramUser(TelegramUserDto userDto);

}
