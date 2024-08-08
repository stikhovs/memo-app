package com.sergio.memo_app.persistence.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;
import java.time.LocalDateTime;

@Table()
public record Card(@Id Long id,
                   String frontSide,
                   String backSide,
                   @Column("card_set_id") AggregateReference<CardSet, Long> cardSet,
                   LocalDateTime createdAt,
                   LocalDateTime updatedAt) implements Serializable {
}