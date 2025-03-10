package com.sergio.memo_app.component.persistence;

import com.sergio.memo_app.component.base.BaseCT;
import com.sergio.memo_app.persistence.dto.TelegramUserDto;
import com.sergio.memo_app.persistence.service.TelegramUserPersistenceService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestTelegramUserPersistenceService extends BaseCT {

    @Autowired
    private TelegramUserPersistenceService telegramUserPersistenceService;

    @Test
    @Order(1)
    void shouldFindAllUsers() {
        List<TelegramUserDto> telegramUsers = telegramUserPersistenceService.findAll();

        assertThat(telegramUsers).hasSize(1);
        assertThat(telegramUsers.getFirst()).isNotNull();
        assertThat(telegramUsers.getFirst().id()).isEqualTo(1);
        assertThat(telegramUsers.getFirst().username()).isEqualTo("test telegram user");
        assertThat(telegramUsers.getFirst().telegramUserId()).isEqualTo(123);
        assertThat(telegramUsers.getFirst().telegramChatId()).isEqualTo(456);
    }

    @Test
    @Order(2)
    void shouldFindUserById() {
        TelegramUserDto telegramUser = telegramUserPersistenceService.findById(1);

        assertThat(telegramUser).isNotNull();
        assertThat(telegramUser.id()).isEqualTo(1);
        assertThat(telegramUser.username()).isEqualTo("test telegram user");
        assertThat(telegramUser.telegramUserId()).isEqualTo(123);
        assertThat(telegramUser.telegramChatId()).isEqualTo(456);
    }

    @Test
    @Order(3)
    void shouldFindUserByTelegramUserId() {
        Optional<TelegramUserDto> telegramUser = telegramUserPersistenceService.findByTelegramUserId(123L);

        assertThat(telegramUser.isPresent()).isTrue();
        assertThat(telegramUser.get().id()).isEqualTo(1);
        assertThat(telegramUser.get().username()).isEqualTo("test telegram user");
        assertThat(telegramUser.get().telegramUserId()).isEqualTo(123);
        assertThat(telegramUser.get().telegramChatId()).isEqualTo(456);
    }

    @Test
    @Order(4)
    void shouldInsertUser() {
        TelegramUserDto telegramUserDto = TelegramUserDto.builder()
                .username("test")
                .telegramChatId(555L)
                .telegramUserId(666L)
                .build();

        telegramUserPersistenceService.insert(telegramUserDto);

        Optional<TelegramUserDto> telegramUser = telegramUserPersistenceService.findByTelegramUserId(666L);
        assertThat(telegramUser.isPresent()).isTrue();
        assertThat(telegramUser.get().id()).isEqualTo(2);
        assertThat(telegramUser.get().username()).isEqualTo("test");
        assertThat(telegramUser.get().telegramChatId()).isEqualTo(555);
        assertThat(telegramUser.get().telegramUserId()).isEqualTo(666);
    }

    @Test
    @Order(5)
    void shouldUpdateTelegramChatId() {
        TelegramUserDto telegramUserDto = TelegramUserDto.builder()
                .id(2)
                .username("test")
                .telegramChatId(555L)
                .telegramUserId(999L)
                .build();

        telegramUserPersistenceService.update(telegramUserDto);

        Optional<TelegramUserDto> telegramUser = telegramUserPersistenceService.findByTelegramUserId(999L);
        assertThat(telegramUser.isPresent()).isTrue();
        assertThat(telegramUser.get().id()).isEqualTo(2);
        assertThat(telegramUser.get().username()).isEqualTo("test");
        assertThat(telegramUser.get().telegramChatId()).isEqualTo(555);
        assertThat(telegramUser.get().telegramUserId()).isEqualTo(999);
    }

    @Test
    @Order(6)
    void shouldUpdateTelegramUserId() {
        TelegramUserDto telegramUserDto = TelegramUserDto.builder()
                .id(2)
                .username("test")
                .telegramChatId(888L)
                .telegramUserId(999L)
                .build();

        telegramUserPersistenceService.update(telegramUserDto);

        Optional<TelegramUserDto> telegramUser = telegramUserPersistenceService.findByTelegramUserId(999L);
        assertThat(telegramUser.isPresent()).isTrue();
        assertThat(telegramUser.get().id()).isEqualTo(2);
        assertThat(telegramUser.get().username()).isEqualTo("test");
        assertThat(telegramUser.get().telegramChatId()).isEqualTo(888);
        assertThat(telegramUser.get().telegramUserId()).isEqualTo(999);
    }

    @Test
    @Order(7)
    void shouldUpdateUsername() {
        TelegramUserDto telegramUserDto = TelegramUserDto.builder()
                .id(2)
                .username("test 123")
                .telegramChatId(555L)
                .telegramUserId(999L)
                .build();

        telegramUserPersistenceService.update(telegramUserDto);

        Optional<TelegramUserDto> telegramUser = telegramUserPersistenceService.findByTelegramUserId(999L);
        assertThat(telegramUser.isPresent()).isTrue();
        assertThat(telegramUser.get().id()).isEqualTo(2);
        assertThat(telegramUser.get().username()).isEqualTo("test 123");
        assertThat(telegramUser.get().telegramChatId()).isEqualTo(555);
        assertThat(telegramUser.get().telegramUserId()).isEqualTo(999);
    }

    @Test
    @Order(8)
    void shouldDeleteUser() {
        telegramUserPersistenceService.delete(2);

        assertThatCode(() -> telegramUserPersistenceService.findById(2))
                .isExactlyInstanceOf(RuntimeException.class)
                .hasMessage("Couldn't find telegram user by id: %s".formatted(2));
    }

}
