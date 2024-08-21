package com.sergio.memo_app.mapper;

import com.sergio.memo_app.api.dto.CardSetDto;
import com.sergio.memo_app.mapper.common.DateTimeHandler;
import com.sergio.memo_app.mapper.common.UuidHandler;
import com.sergio.memo_app.persistence.entity.CardSet;
import com.sergio.memo_app.persistence.repository.projection.TitleAndId;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = CardMapper.class)
public interface CardSetMapper extends DateTimeHandler<CardSetDto>, UuidHandler {

    @Mapping(source = "cardSetDto", target = "createdAt", qualifiedByName = "now")
    @Mapping(source = "cardSetDto", target = "updatedAt", qualifiedByName = "now")
    @Mapping(source = "cardSetDto", target = "uuid", qualifiedByName = "randomUuid")
    CardSet toEntity(CardSetDto cardSetDto);

    CardSetDto toDto(CardSet cardSet);

    CardSetDto toDto(TitleAndId projection);


}
