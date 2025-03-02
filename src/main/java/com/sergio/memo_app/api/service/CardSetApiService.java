package com.sergio.memo_app.api.service;

import com.sergio.memo_app.api.dto.CardSetApiDto;
import com.sergio.memo_app.mapper.ApiMapper;
import com.sergio.memo_app.persistence.dto.CardSetDto;
import com.sergio.memo_app.persistence.service.CardSetPersistenceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CardSetApiService {

    private final ApiMapper mapper;
    private final CardSetPersistenceService cardSetPersistenceService;

    public CardSetApiDto save(CardSetDto data) {
        CardSetDto inserted = cardSetPersistenceService.insert(data);
        return mapper.toCardSet(inserted);
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

    public List<CardSetApiDto> getSets(Long userId, List<Long> ids) {
        List<CardSetDto> cardSetDtoList = cardSetPersistenceService.findAllByUserId(userId, ids);
        return mapper.toCardSets(cardSetDtoList);
    }

    public List<CardSetApiDto> getTitlesAndIds(Long userId) {
        List<CardSetDto> cardSetDtoList = cardSetPersistenceService.getSetIdsAndTitles(userId);
        return mapper.toCardSets(cardSetDtoList);
    }
}
