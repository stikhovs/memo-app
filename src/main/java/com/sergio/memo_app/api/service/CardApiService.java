package com.sergio.memo_app.api.service;

import com.sergio.memo_app.api.dto.CardApiDto;
import com.sergio.memo_app.mapper.ApiMapper;
import com.sergio.memo_app.persistence.dto.CardDto;
import com.sergio.memo_app.persistence.service.CardPersistenceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CardApiService {

    private final ApiMapper mapper;
    private final CardPersistenceService cardPersistenceService;

    public List<CardApiDto> save(Long setId, List<CardDto> cards) {
        List<CardDto> cardDtoList = cardPersistenceService.addCards(setId, cards);
        return mapper.toCards(cardDtoList);
    }

    public List<CardApiDto> find(Long cardSetId) {
        List<CardDto> cardDtoList = cardPersistenceService.findAllBySetId(cardSetId);
        return mapper.toCards(cardDtoList);
    }
    public CardApiDto update(Long cardId, CardDto cardDto) {
        CardDto updatedCard = cardPersistenceService.update(cardDto);
        return mapper.toCard(updatedCard);
    }
}
