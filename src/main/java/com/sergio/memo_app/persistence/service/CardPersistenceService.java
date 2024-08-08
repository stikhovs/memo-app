package com.sergio.memo_app.persistence.service;

import com.sergio.memo_app.api.dto.CardDto;
import com.sergio.memo_app.api.dto.CardSetDto;
import com.sergio.memo_app.mapper.CardMapper;
import com.sergio.memo_app.mapper.CardSetMapper;
import com.sergio.memo_app.persistence.entity.Card;
import com.sergio.memo_app.persistence.repository.CardSetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CardPersistenceService {

    private final CardMapper cardMapper;
    private final CardSetMapper cardSetMapper;
    private final JdbcClient jdbcClient;
    //private final CardRepository cardRepository;
    private final CardSetRepository cardSetRepository;

    @Transactional
    public List<CardDto> addCards(Long setId, List<CardDto> cards) {
        String sql = """
                INSERT INTO card
                (card_set_id, front_side, back_side, created_at, updated_at)
                VALUES(:setId, :frontSide, :backSide, :createdAt, :updatedAt);
                """;
        cards.stream()
                .map(cardMapper::toEntity)
                .forEach(card -> {
                    jdbcClient.sql(sql)
                            .param("setId", setId)
                            .param("frontSide", card.frontSide())
                            .param("backSide", card.backSide())
                            .param("createdAt", card.createdAt())
                            .param("updatedAt", card.updatedAt())
                            .update();
                });

        return findAll(setId);
    }

    public List<CardDto> findAll(Long setId) {
        return cardSetRepository.findById(setId)
                .map(cardSetMapper::toDto)
                .map(CardSetDto::cards)
                .orElseThrow(() -> new RuntimeException("Couldn't find cards by card set id: %s".formatted(setId)));
    }

    @Transactional
    public CardDto update(Long id, CardDto cardDto) {
        String query = """
                UPDATE card
                SET front_side=:frontSide, back_side=:backSide, updated_at=:updatedAt
                WHERE id=:id;
                """;
        Card card = cardMapper.toEntity(cardDto);

        jdbcClient.sql(query)
                .param("id", id)
                .param("frontSide", card.frontSide())
                .param("backSide", card.backSide())
                .param("updatedAt", card.updatedAt())
                .update();

        return jdbcClient.sql("select * from card where id = :id")
                .param("id", id)
                .query(rs -> {
                    rs.next();
                    return CardDto.builder()
                            .id(rs.getLong("id"))
                            .frontSide(rs.getString("front_side"))
                            .backSide(rs.getString("back_side"))
                            .build();
                });
    }

}
