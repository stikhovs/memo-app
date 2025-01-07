package com.sergio.memo_app.component;

import com.sergio.memo_app.ConstantHelper;
import com.sergio.memo_app.api.dto.CardSetDto;
import com.sergio.memo_app.component.base.BaseCT;
import com.sergio.memo_app.persistence.service.CardSetPersistenceService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.sergio.memo_app.ConstantHelper.CardSet.*;
import static com.sergio.memo_app.ConstantHelper.User.USER_ID_1;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestCardSetPersistenceService extends BaseCT {
    @Autowired
    private CardSetPersistenceService cardSetPersistenceService;

    @Test
    @Order(1)
    void shouldFindAllCardSets() {
        List<CardSetDto> cardSetDtoList = cardSetPersistenceService.findAll();

        assertThat(cardSetDtoList).hasSize(1);
        assertThat(cardSetDtoList.getFirst()).isNotNull();
        assertThat(cardSetDtoList.getFirst().id()).isEqualTo(CARD_SET_ID_1);
        assertThat(cardSetDtoList.getFirst().userId()).isEqualTo(USER_ID_1.longValue());
        assertThat(cardSetDtoList.getFirst().title()).isEqualTo(ConstantHelper.CardSet.CARD_SET_TITLE_1);
        assertThat(cardSetDtoList.getFirst().uuid()).isNotNull();
        assertThat(cardSetDtoList.getFirst().uuid().toString()).isEqualTo(ConstantHelper.CardSet.CARD_SET_UUID_1);
    }

    @Test
    @Order(2)
    void shouldFindCardSetById() {
        CardSetDto cardSetDto = cardSetPersistenceService.findById(CARD_SET_ID_1);

        assertThat(cardSetDto).isNotNull();
        assertThat(cardSetDto.id()).isEqualTo(CARD_SET_ID_1);
        assertThat(cardSetDto.userId()).isEqualTo(USER_ID_1.longValue());
        assertThat(cardSetDto.title()).isEqualTo(ConstantHelper.CardSet.CARD_SET_TITLE_1);
        assertThat(cardSetDto.uuid()).isNotNull();
        assertThat(cardSetDto.uuid().toString()).isEqualTo(ConstantHelper.CardSet.CARD_SET_UUID_1);
    }

    @Test
    @Order(3)
    void shouldFindCardSetByTitle() {
        CardSetDto cardSetDto = cardSetPersistenceService.findByTitle(CARD_SET_TITLE_1);

        assertThat(cardSetDto).isNotNull();
        assertThat(cardSetDto.id()).isEqualTo(CARD_SET_ID_1);
        assertThat(cardSetDto.userId()).isEqualTo(USER_ID_1.longValue());
        assertThat(cardSetDto.title()).isEqualTo(ConstantHelper.CardSet.CARD_SET_TITLE_1);
        assertThat(cardSetDto.uuid()).isNotNull();
        assertThat(cardSetDto.uuid().toString()).isEqualTo(ConstantHelper.CardSet.CARD_SET_UUID_1);
    }

    @Test
    @Order(4)
    void shouldFindCardSetsByUserId() {
        List<CardSetDto> cardSetDtoList = cardSetPersistenceService.findAllByUserId(USER_ID_1.longValue());

        assertThat(cardSetDtoList).hasSize(1);
        assertThat(cardSetDtoList.getFirst()).isNotNull();
        assertThat(cardSetDtoList.getFirst().id()).isEqualTo(CARD_SET_ID_1);
        assertThat(cardSetDtoList.getFirst().userId()).isEqualTo(USER_ID_1.longValue());
        assertThat(cardSetDtoList.getFirst().title()).isEqualTo(ConstantHelper.CardSet.CARD_SET_TITLE_1);
        assertThat(cardSetDtoList.getFirst().uuid()).isNotNull();
        assertThat(cardSetDtoList.getFirst().uuid().toString()).isEqualTo(ConstantHelper.CardSet.CARD_SET_UUID_1);
    }

    @Test
    @Order(5)
    void shouldFindCardSetsByUserIdAndSetIds() {
        List<CardSetDto> cardSetDtoList = cardSetPersistenceService.findAllByUserId(USER_ID_1.longValue(), List.of(CARD_SET_ID_1));

        assertThat(cardSetDtoList).hasSize(1);
        assertThat(cardSetDtoList.getFirst()).isNotNull();
        assertThat(cardSetDtoList.getFirst().id()).isEqualTo(CARD_SET_ID_1);
        assertThat(cardSetDtoList.getFirst().userId()).isEqualTo(USER_ID_1.longValue());
        assertThat(cardSetDtoList.getFirst().title()).isEqualTo(ConstantHelper.CardSet.CARD_SET_TITLE_1);
        assertThat(cardSetDtoList.getFirst().uuid()).isNotNull();
        assertThat(cardSetDtoList.getFirst().uuid().toString()).isEqualTo(ConstantHelper.CardSet.CARD_SET_UUID_1);
    }

    @Test
    @Order(6)
    void shouldFindSetIdsAndTitles() {
        List<CardSetDto> cardSetDtoList = cardSetPersistenceService.getSetIdsAndTitles(CARD_SET_ID_1);

        assertThat(cardSetDtoList).hasSize(1);
        assertThat(cardSetDtoList.getFirst()).isNotNull();
        assertThat(cardSetDtoList.getFirst().id()).isEqualTo(CARD_SET_ID_1);
        assertThat(cardSetDtoList.getFirst().userId()).isEqualTo(USER_ID_1.longValue());
        assertThat(cardSetDtoList.getFirst().title()).isEqualTo(ConstantHelper.CardSet.CARD_SET_TITLE_1);
        assertThat(cardSetDtoList.getFirst().uuid()).isNotNull();
        assertThat(cardSetDtoList.getFirst().uuid().toString()).isEqualTo(ConstantHelper.CardSet.CARD_SET_UUID_1);
    }

    @Test
    @Order(7)
    void shouldInsertCardSetWithoutCards() {
        CardSetDto data = CardSetDto.builder()
                .userId(USER_ID_1.longValue())
                .title(CARD_SET_TITLE_2)
                .build();

        cardSetPersistenceService.insert(data);

        CardSetDto cardSetDto = cardSetPersistenceService.findByTitle(CARD_SET_TITLE_2);
        assertThat(cardSetDto).isNotNull();
        assertThat(cardSetDto.id()).isEqualTo(CARD_SET_ID_2);
        assertThat(cardSetDto.userId()).isEqualTo(USER_ID_1.longValue());
        assertThat(cardSetDto.title()).isEqualTo(ConstantHelper.CardSet.CARD_SET_TITLE_2);
        assertThat(cardSetDto.uuid()).isNotNull();
        assertThat(cardSetDto.cards()).isNull();
    }

    @Test
    @Order(8)
    void shouldUpdateCardSet() {
        CardSetDto data = CardSetDto.builder()
                .id(CARD_SET_ID_2)
                .title(CARD_SET_TITLE_2 + " updated")
                .build();

        cardSetPersistenceService.update(data);

        CardSetDto cardSetDto = cardSetPersistenceService.findByTitle(CARD_SET_TITLE_2 + " updated");
        assertThat(cardSetDto).isNotNull();
        assertThat(cardSetDto.id()).isEqualTo(CARD_SET_ID_2);
        assertThat(cardSetDto.userId()).isEqualTo(USER_ID_1.longValue());
        assertThat(cardSetDto.title()).isEqualTo(ConstantHelper.CardSet.CARD_SET_TITLE_2 + " updated");
        assertThat(cardSetDto.uuid()).isNotNull();
        assertThat(cardSetDto.cards()).isNull();
    }

    @Test
    @Order(9)
    void shouldDeleteCardSet() {
        cardSetPersistenceService.delete(CARD_SET_ID_2);

        assertThatCode(() -> cardSetPersistenceService.findById(CARD_SET_ID_2))
                .isExactlyInstanceOf(RuntimeException.class)
                .hasMessage("Couldn't find card_set by id: %s".formatted(CARD_SET_ID_2));
    }

}
