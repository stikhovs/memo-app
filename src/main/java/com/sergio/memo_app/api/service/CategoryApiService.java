package com.sergio.memo_app.api.service;

import com.sergio.memo_app.generated.tables.records.CompositeUserRecord;
import com.sergio.memo_app.persistence.dto.CategoryDto;
import com.sergio.memo_app.persistence.service.CategoryPersistenceService;
import com.sergio.memo_app.persistence.service.CompositeUserPersistenceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryApiService {

    private final CategoryPersistenceService categoryPersistenceService;
    private final CompositeUserPersistenceService compositeUserPersistenceService;

    public CategoryDto save(CategoryDto categoryDto) {
        return categoryPersistenceService.insert(categoryDto);
    }
    public CategoryDto saveFromTelegram(Long chatId, CategoryDto categoryDto) {
        CompositeUserRecord user = compositeUserPersistenceService.findByTelegramChatId(chatId);
        return categoryPersistenceService.insert(categoryDto.toBuilder().userId(user.getId()).build());
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

    public void delete(Long categoryId) {
        categoryPersistenceService.delete(categoryId);
    }

}
