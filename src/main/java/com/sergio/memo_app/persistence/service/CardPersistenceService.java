package com.sergio.memo_app.persistence.service;

import com.sergio.memo_app.generated.tables.Card;
import com.sergio.memo_app.generated.tables.records.CardRecord;
import com.sergio.memo_app.persistence.dto.CardDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.sergio.memo_app.mapper.PersistenceMapper.toCardDto;

@Slf4j
@Service
@RequiredArgsConstructor
public class CardPersistenceService implements BaseCrud<CardDto, Long> {

    private final DSLContext dslContext;

    @Override
    public List<CardDto> findAll() {
        return dslContext.select()
                .from(Card.CARD)
                .fetch(toCardDto());
    }

    public List<CardDto> findAllBySetId(Long setId) {
        return dslContext.select()
                .from(Card.CARD)
                .where(Card.CARD.CARD_SET_ID.eq(setId))
                .fetch(toCardDto());
    }

    @Override
    public CardDto findById(Long id) {
        return dslContext.select()
                .from(Card.CARD)
                .where(Card.CARD.ID.eq(id))
                .fetchOptional()
                .map(toCardDto())
                .orElseThrow(() -> new RuntimeException("Couldn't find card by id: %s".formatted(id)));
    }

    @Override
    public CardDto update(CardDto data) {
        int numberOfRecords = dslContext.update(Card.CARD)
                .set(Card.CARD.FRONT_SIDE, data.frontSide())
                .set(Card.CARD.BACK_SIDE, data.backSide())
                .where(Card.CARD.ID.eq(data.id()))
                .execute();
        return data;
    }

    @Override
    @Deprecated
    public CardDto insert(CardDto data) {
        throw new UnsupportedOperationException("Insert without cardSetId is not supported.");
    }

    public CardDto insert(Long cardSetId, CardDto data) {
        int numberOfRecords = dslContext.insertInto(Card.CARD)
                .set(Card.CARD.CARD_SET_ID, cardSetId)
                .set(Card.CARD.FRONT_SIDE, data.frontSide())
                .set(Card.CARD.BACK_SIDE, data.backSide())
                .execute();
        return data;
    }

    @Override
    public void delete(Long id) {
        dslContext.delete(Card.CARD)
                .where(Card.CARD.ID.eq(id))
                .execute();
    }
    public void deleteBySetId(Long setId) {
        dslContext.delete(Card.CARD)
                .where(Card.CARD.CARD_SET_ID.eq(setId))
                .execute();
    }

    public List<CardDto> addCards(Long setId, List<CardDto> cards) {
        List<CardRecord> cardRecords = cards.stream()
                .map(cardDto -> {
                    CardRecord cardRecord = dslContext.newRecord(Card.CARD);
                    cardRecord.setCardSetId(setId);
                    cardRecord.setBackSide(cardDto.backSide());
                    cardRecord.setFrontSide(cardDto.frontSide());
                    return cardRecord;
                })
                .toList();

        dslContext.insertInto(Card.CARD)
                .set(cardRecords)
                .execute();

        return findAllBySetId(setId);
    }
}
