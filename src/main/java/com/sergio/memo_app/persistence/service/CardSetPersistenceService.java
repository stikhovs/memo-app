package com.sergio.memo_app.persistence.service;

import com.sergio.memo_app.api.dto.CardSetDto;
import com.sergio.memo_app.mapper.CardSetMapper;
import com.sergio.memo_app.persistence.entity.CardSet;
import com.sergio.memo_app.persistence.repository.CardSetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CardSetPersistenceService {

    private final CardSetMapper cardSetMapper;
    private final CardSetRepository cardSetRepository;


    public CardSetDto save(CardSetDto cardSetDto) {
        CardSet savedCardSet = cardSetRepository.save(cardSetMapper.toEntity(cardSetDto));
        return cardSetMapper.toDto(savedCardSet);
    }

    public CardSetDto find(String title) {
        return cardSetRepository.findByTitle(title)
                .map(cardSetMapper::toDto)
                .orElseThrow(() -> new RuntimeException("Couldn't find CardSet by title: %s".formatted(title)));
    }

    public List<CardSetDto> findAll(Long userId) {
        return cardSetRepository.findByUserId(userId)
                .stream()
                .map(cardSetMapper::toDto)
                .toList();
    }

    public List<CardSetDto> findAll(Long userId, List<Long> ids) {
        return cardSetRepository.findByUserIdAndIdIn(userId, ids)
                .stream()
                .map(cardSetMapper::toDto)
                .toList();
    }

    public List<CardSetDto> getSetIdsAndTitles(Long userId) {
        return cardSetRepository.getByUserId(userId)
                .stream()
                .map(cardSetMapper::toDto)
                .toList();
    }

    public CardSetDto findById(Long id) {
        return cardSetRepository.findById(id)
                .map(cardSetMapper::toDto)
                .orElseThrow(() -> new RuntimeException("Couldn't find CardSet by id: %s".formatted(id)));
    }
}
