package com.sergio.memo_app.api.service;

import com.sergio.memo_app.generated.tables.records.CompositeUserRecord;
import com.sergio.memo_app.persistence.dto.CardSetDto;
import com.sergio.memo_app.persistence.dto.CategoryDto;
import com.sergio.memo_app.persistence.service.CardPersistenceService;
import com.sergio.memo_app.persistence.service.CardSetPersistenceService;
import com.sergio.memo_app.persistence.service.CategoryPersistenceService;
import com.sergio.memo_app.persistence.service.CompositeUserPersistenceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryApiService {

    private final CategoryPersistenceService categoryPersistenceService;
    private final CompositeUserPersistenceService compositeUserPersistenceService;
    private final CardSetPersistenceService cardSetPersistenceService;
    private final CardPersistenceService cardPersistenceService;

    public CategoryDto save(CategoryDto categoryDto) {
        return categoryPersistenceService.insert(categoryDto);
    }

    public CategoryDto saveFromTelegram(Long chatId, CategoryDto categoryDto) {
        CompositeUserRecord user = compositeUserPersistenceService.findByTelegramChatId(chatId);
        return categoryPersistenceService.insert(categoryDto.toBuilder()
                .userId(user.getId())
                .build());
    }

    public CategoryDto update(CategoryDto categoryDto) {
        return categoryPersistenceService.update(categoryDto);
    }

    public CategoryDto getById(Long categoryId) {
        return categoryPersistenceService.findById(categoryId);
    }

    public List<CategoryDto> findAllByUserId(Integer userId) {
        return categoryPersistenceService.findAllByUserId(userId);
    }

    public List<CategoryDto> findAllByChatId(Long chatId) {
        CompositeUserRecord user = compositeUserPersistenceService.findByTelegramChatId(chatId);
        return categoryPersistenceService.findAllByUserId(user.getId());
    }

    @Transactional
    public void delete(Long categoryId, boolean keepSets) {
        List<CardSetDto> cardSets = cardSetPersistenceService.findAllByCategoryId(categoryId);
        if (keepSets) {
            if (!cardSets.isEmpty()) {
                Integer userId = cardSets.getFirst().userId();
                CategoryDto defaultCategory = categoryPersistenceService.getDefault(userId);
                List<Long> cardSetIds = cardSets.stream().map(CardSetDto::id).toList();
                cardSetPersistenceService.updateCategory(defaultCategory.id(), cardSetIds);
            }
        } else {
            List<Long> cardSetIds = cardSets.stream().map(CardSetDto::id).toList();
            cardPersistenceService.deleteBySetIds(cardSetIds);
            cardSetPersistenceService.deleteAll(cardSetIds);
        }
        categoryPersistenceService.delete(categoryId);
    }

}
