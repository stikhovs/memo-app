package com.sergio.memo_app.component.persistence;

import com.sergio.memo_app.component.base.BaseCT;
import com.sergio.memo_app.persistence.dto.CardDto;
import com.sergio.memo_app.persistence.service.CardPersistenceService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.sergio.memo_app.util.ConstantHelper.Card.*;
import static com.sergio.memo_app.util.ConstantHelper.CardSet.CARD_SET_ID_1;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestCardPersistenceService extends BaseCT {
    @Autowired
    private CardPersistenceService cardPersistenceService;

    @Test
    @Order(1)
    void shouldFindAll() {
        List<CardDto> cardDtoList = cardPersistenceService.findAll();

        assertThat(cardDtoList).hasSize(5);
        for (int i = 0; i < cardDtoList.size(); i++) {
            assertThat(cardDtoList.get(i)).isNotNull();
            assertThat(cardDtoList.get(i).id()).isEqualTo(getId(i + 1));
            assertThat(cardDtoList.get(i).frontSide()).isEqualTo(getFrontSide(i + 1));
            assertThat(cardDtoList.get(i).backSide()).isEqualTo(getBackSide(i + 1));
        }
    }

    @Test
    @Order(2)
    void shouldFindAllBySetId() {
        List<CardDto> cardDtoList = cardPersistenceService.findAllBySetId(CARD_SET_ID_1);

        assertThat(cardDtoList).hasSize(5);
        for (int i = 0; i < cardDtoList.size(); i++) {
            assertThat(cardDtoList.get(i)).isNotNull();
            assertThat(cardDtoList.get(i).id()).isEqualTo(getId(i + 1));
            assertThat(cardDtoList.get(i).frontSide()).isEqualTo(getFrontSide(i + 1));
            assertThat(cardDtoList.get(i).backSide()).isEqualTo(getBackSide(i + 1));
        }
    }

    @Test
    @Order(3)
    void shouldFindById() {
        Long cardId = 1L;
        CardDto cardDto = cardPersistenceService.findById(cardId);

        assertThat(cardDto).isNotNull();
        assertThat(cardDto.id()).isEqualTo(getId(cardId.intValue()));
        assertThat(cardDto.frontSide()).isEqualTo(getFrontSide(cardId.intValue()));
        assertThat(cardDto.backSide()).isEqualTo(getBackSide(cardId.intValue()));
    }

    @Test
    @Order(4)
    void shouldAddCards() {
        String frontSide6 = "front 6";
        String backSide6 = "back 6";
        String frontSide7 = "front 7";
        String backSide7 = "back 7";

        cardPersistenceService.addCards(
                CARD_SET_ID_1,
                List.of(
                        CardDto.builder().frontSide(frontSide6).backSide(backSide6).build(),
                        CardDto.builder().frontSide(frontSide7).backSide(backSide7).build()
                )
        );

        List<CardDto> cardDtoList = cardPersistenceService.findAllBySetId(CARD_SET_ID_1);

        assertThat(cardDtoList).hasSize(7);
        for (int i = 0; i < cardDtoList.size(); i++) {
            assertThat(cardDtoList.get(i)).isNotNull();
            assertThat(cardDtoList.get(i).id()).isEqualTo(getId(i + 1));
            assertThat(cardDtoList.get(i).frontSide()).isEqualTo(getFrontSide(i + 1));
            assertThat(cardDtoList.get(i).backSide()).isEqualTo(getBackSide(i + 1));
        }
    }

    @Test
    @Order(5)
    void shouldInsertUnsupported() {
        assertThatCode(() -> cardPersistenceService.insert(CardDto.builder().build()))
                .isExactlyInstanceOf(UnsupportedOperationException.class)
                .hasMessage("Insert without cardSetId is not supported.");
    }

    @Test
    @Order(6)
    void shouldInsert() {
        Long cardId = 8L;
        String frontSide = "test front side";
        String backSide = "test back side";
        cardPersistenceService.insert(
                CARD_SET_ID_1,
                CardDto.builder()
                        .frontSide(frontSide)
                        .backSide(backSide)
                        .build());

        CardDto cardDto = cardPersistenceService.findById(cardId);
        assertThat(cardDto).isNotNull();
        assertThat(cardDto.id()).isEqualTo(cardId);
        assertThat(cardDto.frontSide()).isEqualTo(frontSide);
        assertThat(cardDto.backSide()).isEqualTo(backSide);
    }

    @Test
    @Order(7)
    void shouldUpdate() {
        Long cardId = 8L;
        String frontSide = "Updated front side";
        String backSide = "Updated back side";

        cardPersistenceService.update(
                CardDto.builder()
                        .id(cardId)
                        .frontSide(frontSide)
                        .backSide(backSide)
                        .build()
        );

        CardDto cardDto = cardPersistenceService.findById(cardId);
        assertThat(cardDto).isNotNull();
        assertThat(cardDto.id()).isEqualTo(cardId);
        assertThat(cardDto.frontSide()).isEqualTo(frontSide);
        assertThat(cardDto.backSide()).isEqualTo(backSide);
    }

    @Test
    @Order(8)
    void shouldDelete() {
        Long cardId = 8L;
        cardPersistenceService.delete(cardId);

        assertThatCode(() -> cardPersistenceService.findById(cardId))
                .isExactlyInstanceOf(RuntimeException.class)
                .hasMessage("Couldn't find card by id: %s".formatted(cardId));
    }

}
