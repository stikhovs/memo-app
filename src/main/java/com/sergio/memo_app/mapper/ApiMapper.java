package com.sergio.memo_app.mapper;

import com.sergio.memo_app.api.dto.CardApiDto;
import com.sergio.memo_app.api.dto.CardSetApiDto;
import com.sergio.memo_app.api.dto.UserApiDto;
import com.sergio.memo_app.persistence.dto.CardDto;
import com.sergio.memo_app.persistence.dto.CardSetDto;
import com.sergio.memo_app.persistence.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ApiMapper {

    CardApiDto toCard(CardDto cardDto);
    CardSetApiDto toCardSet(CardSetDto cardSetDto);
    UserApiDto toUser(UserDto userDto);

}
