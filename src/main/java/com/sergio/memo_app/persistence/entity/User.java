package com.sergio.memo_app.persistence.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;
import java.time.LocalDateTime;

@Table(name = "app_user")
public record User(@Id Integer id,
                   String username,
                   String email,
                   LocalDateTime createdAt,
                   LocalDateTime updatedAt) implements Serializable {
}
