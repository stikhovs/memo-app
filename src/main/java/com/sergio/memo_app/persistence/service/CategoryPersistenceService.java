package com.sergio.memo_app.persistence.service;

import com.sergio.memo_app.generated.tables.Category;
import com.sergio.memo_app.generated.tables.CompositeUser;
import com.sergio.memo_app.generated.tables.TelegramUser;
import com.sergio.memo_app.persistence.dto.CategoryDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.sergio.memo_app.mapper.PersistenceMapper.toCategoryDto;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryPersistenceService implements BaseCrud<CategoryDto, Long> {

    private final DSLContext dslContext;

    @Override
    public List<CategoryDto> findAll() {
        return dslContext
                .select()
                .from(Category.CATEGORY)
                .fetch(toCategoryDto());
    }

    @Override
    public CategoryDto findById(Long id) {
        return dslContext.select()
                .from(Category.CATEGORY)
                .where(Category.CATEGORY.ID.eq(id))
                .fetchOptional()
                .map(toCategoryDto())
                .orElseThrow(() -> new RuntimeException("Couldn't find category by id: %s".formatted(id)));
    }

    @Override
    public CategoryDto update(CategoryDto data) {
        int numberOfRecords = dslContext.update(Category.CATEGORY)
                .set(Category.CATEGORY.TITLE, data.title())
                .where(Category.CATEGORY.ID.eq(data.id()))
                .execute();
        return findById(data.id());
    }

    @Override
    @Transactional
    public CategoryDto insert(CategoryDto data) {
        int numberOfRecords = dslContext.insertInto(Category.CATEGORY)
                .set(Category.CATEGORY.TITLE, data.title())
                .set(Category.CATEGORY.USER_ID, data.userId())
                .set(Category.CATEGORY.IS_DEFAULT, false)
                .execute();
        return getByUserIdAndTitle(data.userId(), data.title());
    }

    @Transactional
    public CategoryDto insertDefaultCategory(Integer userId) {
        log.info("Creating default category for userId {}", userId);
        String title = UUID.randomUUID().toString();
        int numberOfRecords = dslContext.insertInto(Category.CATEGORY)
                .set(Category.CATEGORY.TITLE, title)
                .set(Category.CATEGORY.USER_ID, userId)
                .set(Category.CATEGORY.IS_DEFAULT, true)
                .execute();
        return getByUserIdAndTitle(userId, title);
    }

    @Override
    public void delete(Long id) {
        dslContext.delete(Category.CATEGORY)
                .where(Category.CATEGORY.ID.eq(id))
                .execute();
    }


    public List<CategoryDto> findAllByUserId(Integer userId) {
        return dslContext.select()
                .from(Category.CATEGORY)
                .where(Category.CATEGORY.USER_ID.eq(userId))
                .fetch(toCategoryDto());
    }


    public Optional<CategoryDto> findByUserIdAndTitle(Integer userId, String title) {
        return dslContext.select()
                .from(Category.CATEGORY)
                .where(Category.CATEGORY.USER_ID.eq(userId))
                .and(Category.CATEGORY.TITLE.eq(title))
                .fetchOptional()
                .map(toCategoryDto());
    }


    public Optional<CategoryDto> findDefault(Integer userId) {
        return dslContext.select()
                .from(Category.CATEGORY)
                .where(Category.CATEGORY.USER_ID.eq(userId))
                .and(Category.CATEGORY.IS_DEFAULT.eq(true))
                .fetchOptional()
                .map(toCategoryDto());
    }


    public CategoryDto getDefault(Integer userId) {
        return findDefault(userId)
                .orElseThrow(() -> new RuntimeException("Couldn't find default category by userId [%s]".formatted(userId)));
    }


    public CategoryDto getByUserIdAndTitle(Integer userId, String title) {
        return findByUserIdAndTitle(userId, title)
                .orElseThrow(() -> new RuntimeException("Couldn't find category by userId [%s] and title [%s]".formatted(userId, title)));
    }


    public Optional<CategoryDto> findByTelegramChatIdAndTitle(Long telegramChatId, String title) {
        return dslContext.select()
                .from(Category.CATEGORY)
                .join(CompositeUser.COMPOSITE_USER)
                .on(CompositeUser.COMPOSITE_USER.ID.eq(Category.CATEGORY.USER_ID))
                .join(TelegramUser.TELEGRAM_USER)
                .on(TelegramUser.TELEGRAM_USER.ID.eq(CompositeUser.COMPOSITE_USER.TELEGRAM_USER_ID))
                .where(TelegramUser.TELEGRAM_USER.TELEGRAM_CHAT_ID.eq(telegramChatId))
                .and(Category.CATEGORY.TITLE.eq(title))
                .fetchOptional()
                .map(toCategoryDto());
    }


    public CategoryDto getByTelegramChatIdAndTitle(Long telegramChatId, String title) {
        return findByTelegramChatIdAndTitle(telegramChatId, title)
                .orElseThrow(() -> new RuntimeException("Couldn't find category by telegramChatId [%s] and title [%s]".formatted(telegramChatId, title)));
    }
}
