package com.sergio.memo_app.persistence.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.MappedCollection;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

public record CardSet(@Id Long id,
                      String title,
                      UUID uuid,
                      Long userId,
                      @MappedCollection(idColumn = "card_set_id") Set<Card> cards,
                      LocalDateTime createdAt,
                      LocalDateTime updatedAt) implements Serializable {}
