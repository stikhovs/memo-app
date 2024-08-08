package com.sergio.memo_app.mapper;

import com.sergio.memo_app.api.dto.CardDto;
import com.sergio.memo_app.mapper.common.DateTimeHandler;
import com.sergio.memo_app.persistence.entity.Card;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CardMapper extends DateTimeHandler<CardDto> {
    @Mapping(source = "cardDto", target = "createdAt", qualifiedByName = "now")
    @Mapping(source = "cardDto", target = "updatedAt", qualifiedByName = "now")
    Card toEntity(CardDto cardDto);
    CardDto toDto(Card card);
    List<CardDto> toCardDtoList(List<Card> cards);
    List<Card> toEntityList(List<CardDto> cards);
}
