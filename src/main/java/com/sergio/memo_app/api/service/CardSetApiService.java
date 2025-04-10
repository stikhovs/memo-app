package com.sergio.memo_app.api.service;

import com.sergio.memo_app.api.dto.CardSetApiDto;
import com.sergio.memo_app.mapper.ApiMapper;
import com.sergio.memo_app.persistence.dto.CardSetDto;
import com.sergio.memo_app.persistence.service.CardSetPersistenceService;
import com.sergio.memo_app.persistence.service.TelegramUserPersistenceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CardSetApiService {

    private final ApiMapper mapper;
    private final CardSetPersistenceService cardSetPersistenceService;
    private final TelegramUserPersistenceService telegramUserPersistenceService;
    private final CardApiService cardApiService;

    public CardSetApiDto save(CardSetDto data) {
        if (data.telegramChatId() != null) {
            return telegramUserPersistenceService.findByTelegramChatId(data.telegramChatId())
                    .map(telegramUserDto -> data.toBuilder().telegramChatId(null).userId(telegramUserDto.id()).build())
                    .map(this::save)
                    .orElseThrow(() -> new RuntimeException("Couldn't find telegram user by chatId: %s".formatted(data.telegramChatId())));
        } else {
            CardSetDto inserted = cardSetPersistenceService.insert(data);
            cardApiService.save(inserted.id(), data.cards());
            return mapper.toCardSet(inserted);
        }
    }

    public CardSetApiDto findByTitle(String title) {
        CardSetDto cardSetDto = cardSetPersistenceService.findByTitle(title);
        return mapper.toCardSet(cardSetDto);
    }

    public CardSetApiDto findById(Long id) {
        CardSetDto cardSetDto = cardSetPersistenceService.findById(id);
        return mapper.toCardSet(cardSetDto);
    }

    public List<CardSetApiDto> findByUserId(Long userId) {
        List<CardSetDto> cardSetDtoList = cardSetPersistenceService.findAllByUserId(userId);
        return mapper.toCardSets(cardSetDtoList);
    }

    public List<CardSetApiDto> findByTelegramChatId(Long telegramChatId) {
        List<CardSetDto> cardSetDtoList = cardSetPersistenceService.findByTelegramChatId(telegramChatId);
        return mapper.toCardSets(cardSetDtoList);
    }

    public List<CardSetApiDto> getSets(Long userId, List<Long> ids) {
        List<CardSetDto> cardSetDtoList = cardSetPersistenceService.findAllByUserId(userId, ids);
        return mapper.toCardSets(cardSetDtoList);
    }

    public List<CardSetApiDto> getTitlesAndIds(Long userId) {
        List<CardSetDto> cardSetDtoList = cardSetPersistenceService.getSetIdsAndTitles(userId);
        return mapper.toCardSets(cardSetDtoList);
    }

    public CardSetApiDto findBySetId(Long setId) {
        CardSetDto cardSetDto = cardSetPersistenceService.findById(setId);
        return mapper.toCardSet(cardSetDto)
                .toBuilder()
                .cards(cardApiService.find(setId))
                .build();
    }

    public CardSetApiDto update(CardSetDto data) {
        CardSetDto result = cardSetPersistenceService.update(data);
        return mapper.toCardSet(result);
    }

    @Transactional
    public void delete(Long setId) {
        cardApiService.deleteBySetId(setId);
        cardSetPersistenceService.delete(setId);
    }
}
