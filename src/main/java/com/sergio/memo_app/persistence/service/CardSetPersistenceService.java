package com.sergio.memo_app.persistence.service;

import com.sergio.memo_app.generated.tables.Card;
import com.sergio.memo_app.generated.tables.CardSet;
import com.sergio.memo_app.generated.tables.CompositeUser;
import com.sergio.memo_app.generated.tables.TelegramUser;
import com.sergio.memo_app.persistence.dto.CardSetDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.sergio.memo_app.mapper.PersistenceMapper.toCardSetDto;

@Slf4j
@Service
@RequiredArgsConstructor
public class CardSetPersistenceService implements BaseCrud<CardSetDto, Long> {

    private final DSLContext dslContext;

    @Override
    public List<CardSetDto> findAll() {
        return dslContext
                .select()
                .from(CardSet.CARD_SET)
                .fetch(toCardSetDto());
    }

    @Override
    public CardSetDto findById(Long id) {
        return dslContext.select()
                .from(CardSet.CARD_SET)
                .where(CardSet.CARD_SET.ID.eq(id))
                .fetchOptional()
                .map(toCardSetDto())
                .orElseThrow(() -> new RuntimeException("Couldn't find card_set by id: %s".formatted(id)));
    }

    @Override
    public CardSetDto update(CardSetDto data) {
        int numberOfRecords = dslContext.update(CardSet.CARD_SET)
                .set(CardSet.CARD_SET.TITLE, data.title())
                .set(CardSet.CARD_SET.CATEGORY_ID, data.categoryId())
                .where(CardSet.CARD_SET.ID.eq(data.id()))
                .execute();
        return data;
    }

    @Override
    public CardSetDto insert(CardSetDto data) {
        UUID uuid = Optional.ofNullable(data.uuid()).orElse(UUID.randomUUID());
        int numberOfRecords = dslContext.insertInto(CardSet.CARD_SET)
                .set(CardSet.CARD_SET.TITLE, data.title())
                .set(CardSet.CARD_SET.UUID, uuid)
                .set(CardSet.CARD_SET.USER_ID, data.userId())
                .set(CardSet.CARD_SET.CATEGORY_ID, data.categoryId())
                .execute();
        return findByUuid(uuid);
    }

    @Override
    public void delete(Long id) {
        dslContext.delete(CardSet.CARD_SET)
                .where(CardSet.CARD_SET.ID.eq(id))
                .execute();
    }

    public CardSetDto findByTitle(String title) {
        return dslContext.select()
                .from(CardSet.CARD_SET)
                .where(CardSet.CARD_SET.TITLE.eq(title))
                .fetchOptional()
                .map(toCardSetDto())
                .orElseThrow(() -> new RuntimeException("Couldn't find card_set by title: %s".formatted(title)));
    }

    public CardSetDto findByUuid(UUID uuid) {
        return dslContext.select()
                .from(CardSet.CARD_SET)
                .where(CardSet.CARD_SET.UUID.eq(uuid))
                .fetchOptional()
                .map(toCardSetDto())
                .orElseThrow(() -> new RuntimeException("Couldn't find card_set by uuid: %s".formatted(uuid)));
    }

    public List<CardSetDto> findAllByUserId(Long userId) {
        return dslContext.select()
                .from(CardSet.CARD_SET)
                .where(CardSet.CARD_SET.USER_ID.eq(userId.intValue()))
                .fetch(toCardSetDto());
    }

    public List<CardSetDto> findAllByCategoryId(Long categoryId) {
        return dslContext.select()
                .from(CardSet.CARD_SET)
                .where(CardSet.CARD_SET.CATEGORY_ID.eq(categoryId))
                .fetch(toCardSetDto());
    }

    public List<CardSetDto> findAllByUserId(Long userId, List<Long> ids) {
        return dslContext.select()
                .from(CardSet.CARD_SET)
                .join(Card.CARD)
                .on(Card.CARD.CARD_SET_ID.eq(CardSet.CARD_SET.ID))
                .where(CardSet.CARD_SET.USER_ID.eq(userId.intValue()))
                .and(Card.CARD.ID.in(ids))
                .fetch(toCardSetDto());
    }

    public List<CardSetDto> getSetIdsAndTitles(Long userId) {
        return dslContext.select()
                .from(CardSet.CARD_SET)
                .where(CardSet.CARD_SET.USER_ID.eq(userId.intValue()))
                .fetch(toCardSetDto());
    }

    public List<CardSetDto> findByTelegramChatId(Long telegramChatId) {
        return dslContext.select()
                .from(CardSet.CARD_SET)
                .join(CompositeUser.COMPOSITE_USER)
                .on(CompositeUser.COMPOSITE_USER.ID.eq(CardSet.CARD_SET.USER_ID))
                .join(TelegramUser.TELEGRAM_USER)
                .on(TelegramUser.TELEGRAM_USER.ID.eq(CompositeUser.COMPOSITE_USER.TELEGRAM_USER_ID))
                .where(TelegramUser.TELEGRAM_USER.TELEGRAM_CHAT_ID.eq(telegramChatId))
                .fetch(toCardSetDto());
    }
}
