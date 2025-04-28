package com.sergio.memo_app.persistence.service;

import com.sergio.memo_app.generated.tables.TelegramUser;
import com.sergio.memo_app.generated.tables.records.CompositeUserRecord;
import com.sergio.memo_app.persistence.dto.TelegramUserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.sergio.memo_app.mapper.PersistenceMapper.toTelegramUserDto;

@Slf4j
@Service
@RequiredArgsConstructor
public class TelegramUserPersistenceService implements BaseCrud<TelegramUserDto, Integer> {

    private final DSLContext dslContext;
    private final CompositeUserPersistenceService compositeUserPersistenceService;
    private final CategoryPersistenceService categoryPersistenceService;

    @Override
    public List<TelegramUserDto> findAll() {
        log.info("Searching for all telegram users");
        return dslContext
                .select()
                .from(TelegramUser.TELEGRAM_USER)
                .fetch(toTelegramUserDto());
    }

    @Override
    public TelegramUserDto findById(Integer id) {
        log.info("Searching for a telegram user by id [{}]", id);
        return dslContext.select()
                .from(TelegramUser.TELEGRAM_USER)
                .where(TelegramUser.TELEGRAM_USER.ID.eq(id))
                .fetchOptional()
                .map(toTelegramUserDto())
                .orElseThrow(() -> new RuntimeException("Couldn't find telegram user by id: %s".formatted(id)));
    }

    @Override
    public TelegramUserDto update(TelegramUserDto data) {
        log.info("Updating {}", data);
        int numberOfRecords = dslContext.update(TelegramUser.TELEGRAM_USER)
                .set(TelegramUser.TELEGRAM_USER.USERNAME, data.username())
                .set(TelegramUser.TELEGRAM_USER.TELEGRAM_USER_ID, data.telegramUserId())
                .set(TelegramUser.TELEGRAM_USER.TELEGRAM_CHAT_ID, data.telegramChatId())
                .where(TelegramUser.TELEGRAM_USER.ID.eq(data.id()))
                .execute();
        return data;
    }

    @Override
    @Transactional
    public TelegramUserDto insert(TelegramUserDto data) {
        log.info("Inserting {}", data);
        int numberOfRecords = dslContext.insertInto(TelegramUser.TELEGRAM_USER)
                .set(TelegramUser.TELEGRAM_USER.USERNAME, data.username())
                .set(TelegramUser.TELEGRAM_USER.TELEGRAM_USER_ID, data.telegramUserId())
                .set(TelegramUser.TELEGRAM_USER.TELEGRAM_CHAT_ID, data.telegramChatId())
                .execute();

        log.info("Binding to composite user table");
        TelegramUserDto savedUser = findByTelegramUserId(data.telegramUserId()).orElseThrow();
        CompositeUserRecord compositeUserRecord = new CompositeUserRecord();
        compositeUserRecord.setTelegramUserId(savedUser.id());
        CompositeUserRecord createdUser = compositeUserPersistenceService.insert(compositeUserRecord);

        categoryPersistenceService.insertDefaultCategory(createdUser.getId());

        return savedUser;
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        log.info("Deleting telegram user with id [{}]", id);
        compositeUserPersistenceService.findByTelegramUserId(id)
                .ifPresent(it -> compositeUserPersistenceService.delete(it.getId()));

        dslContext.delete(TelegramUser.TELEGRAM_USER)
                .where(TelegramUser.TELEGRAM_USER.ID.eq(id))
                .execute();
    }

    public Optional<TelegramUserDto> findByTelegramUserId(Long telegramUserId) {
        log.info("Searching for a telegram user with telegram userId [{}]", telegramUserId);
        return dslContext.select()
                .from(TelegramUser.TELEGRAM_USER)
                .where(TelegramUser.TELEGRAM_USER.TELEGRAM_USER_ID.eq(telegramUserId))
                .fetchOptional()
                .map(toTelegramUserDto());
    }

    public Optional<TelegramUserDto> findByTelegramChatId(Long telegramChatId) {
        log.info("Searching for a telegram user with telegram chatId [{}]", telegramChatId);
        return dslContext.select()
                .from(TelegramUser.TELEGRAM_USER)
                .where(TelegramUser.TELEGRAM_USER.TELEGRAM_CHAT_ID.eq(telegramChatId))
                .fetchOptional()
                .map(toTelegramUserDto());
    }
}
