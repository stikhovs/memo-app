package com.sergio.memo_app.api.service;

import com.sergio.memo_app.api.dto.CardSetApiDto;
import com.sergio.memo_app.generated.tables.records.CompositeUserRecord;
import com.sergio.memo_app.mapper.ApiMapper;
import com.sergio.memo_app.persistence.dto.CardSetDto;
import com.sergio.memo_app.persistence.dto.CategoryDto;
import com.sergio.memo_app.persistence.dto.constant.CategoryConstant;
import com.sergio.memo_app.persistence.service.CardSetPersistenceService;
import com.sergio.memo_app.persistence.service.CategoryPersistenceService;
import com.sergio.memo_app.persistence.service.CompositeUserPersistenceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CardSetApiService {

    private final ApiMapper mapper;
    private final CardSetPersistenceService cardSetPersistenceService;
    private final CardApiService cardApiService;
    private final CategoryPersistenceService categoryPersistenceService;
    private final CompositeUserPersistenceService compositeUserPersistenceService;

    @Transactional
    public CardSetApiDto saveFromTelegram(CardSetDto data) {
        CompositeUserRecord user = compositeUserPersistenceService.findByTelegramChatId(data.telegramChatId());
        CardSetDto dataWithUser = data.toBuilder().userId(user.getId()).build();
        CardSetDto preparedData = dataWithUser.toBuilder().categoryId(getCategoryId(dataWithUser)).build();
        return save(preparedData);
    }

    @Transactional
    public CardSetApiDto save(CardSetDto data) {
        CardSetDto inserted = cardSetPersistenceService.insert(data);
        cardApiService.save(inserted.id(), data.cards());
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

    public List<CardSetApiDto> findByCategoryId(Long categoryId) {
        List<CardSetDto> cardSetDtoList = cardSetPersistenceService.findAllByCategoryId(categoryId);
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

    public void updateCategoryBatch(Long categoryId, List<Long> cardSetIds) {
        cardSetPersistenceService.updateCategory(categoryId, cardSetIds);
    }

    @Transactional
    public void delete(Long setId) {
        cardApiService.deleteBySetId(setId);
        cardSetPersistenceService.delete(setId);
    }

    private Long getCategoryId(CardSetDto data) {
        if (data.categoryId() == null) {
            Optional<CategoryDto> defaultCategory = categoryPersistenceService.findByUserIdAndTitle(data.userId(), CategoryConstant.DEFAULT_CATEGORY);
            if (defaultCategory.isPresent()) {
                return defaultCategory.get().id();
            }
            CategoryDto category = CategoryDto.builder().userId(data.userId()).build();
            return categoryPersistenceService.insert(category).id();
        }
        return data.categoryId();
    }
}
