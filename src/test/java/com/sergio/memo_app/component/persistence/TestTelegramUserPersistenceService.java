package com.sergio.memo_app.component.persistence;

import com.sergio.memo_app.component.base.BaseCT;
import com.sergio.memo_app.persistence.dto.TelegramUserDto;
import com.sergio.memo_app.persistence.dto.constant.CategoryConstant;
import com.sergio.memo_app.persistence.service.CategoryPersistenceService;
import com.sergio.memo_app.persistence.service.CompositeUserPersistenceService;
import com.sergio.memo_app.persistence.service.TelegramUserPersistenceService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

import static com.sergio.memo_app.util.ConstantHelper.Telegram.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestTelegramUserPersistenceService extends BaseCT {

    @Autowired
    private TelegramUserPersistenceService telegramUserPersistenceService;
    @Autowired
    private CompositeUserPersistenceService compositeUserPersistenceService;
    @Autowired
    private CategoryPersistenceService categoryPersistenceService;

    @Test
    @Order(1)
    void shouldFindAllUsers() {
        List<TelegramUserDto> telegramUsers = telegramUserPersistenceService.findAll();

        assertThat(telegramUsers).hasSize(1);
        assertThat(telegramUsers.getFirst()).isNotNull();
        assertThat(telegramUsers.getFirst().id()).isEqualTo(1);
        assertThat(telegramUsers.getFirst().username()).isEqualTo(TELEGRAM_USERNAME_1);
        assertThat(telegramUsers.getFirst().telegramUserId()).isEqualTo(TELEGRAM_USER_ID_1);
        assertThat(telegramUsers.getFirst().telegramChatId()).isEqualTo(TELEGRAM_CHAT_ID_1);
    }

    @Test
    @Order(2)
    void shouldFindUserById() {
        TelegramUserDto telegramUser = telegramUserPersistenceService.findById(1);

        assertThat(telegramUser).isNotNull();
        assertThat(telegramUser.id()).isEqualTo(1);
        assertThat(telegramUser.username()).isEqualTo(TELEGRAM_USERNAME_1);
        assertThat(telegramUser.telegramUserId()).isEqualTo(TELEGRAM_USER_ID_1);
        assertThat(telegramUser.telegramChatId()).isEqualTo(TELEGRAM_CHAT_ID_1);
    }

    @Test
    @Order(3)
    void shouldFindUserByTelegramUserId() {
        Optional<TelegramUserDto> telegramUser = telegramUserPersistenceService.findByTelegramUserId(123L);

        assertThat(telegramUser.isPresent()).isTrue();
        assertThat(telegramUser.get().id()).isEqualTo(1);
        assertThat(telegramUser.get().username()).isEqualTo(TELEGRAM_USERNAME_1);
        assertThat(telegramUser.get().telegramUserId()).isEqualTo(TELEGRAM_USER_ID_1);
        assertThat(telegramUser.get().telegramChatId()).isEqualTo(TELEGRAM_CHAT_ID_1);
    }

    @Test
    @Order(4)
    void shouldInsertUser() {
        TelegramUserDto telegramUserDto = TelegramUserDto.builder()
                .username(TELEGRAM_USERNAME_2)
                .telegramUserId(TELEGRAM_USER_ID_2)
                .telegramChatId(TELEGRAM_CHAT_ID_2)
                .build();

        telegramUserPersistenceService.insert(telegramUserDto);

        Optional<TelegramUserDto> telegramUser = telegramUserPersistenceService.findByTelegramUserId(TELEGRAM_USER_ID_2);
        assertThat(telegramUser.isPresent()).isTrue();
        assertThat(telegramUser.get().id()).isEqualTo(2);
        assertThat(telegramUser.get().username()).isEqualTo(TELEGRAM_USERNAME_2);
        assertThat(telegramUser.get().telegramUserId()).isEqualTo(TELEGRAM_USER_ID_2);
        assertThat(telegramUser.get().telegramChatId()).isEqualTo(TELEGRAM_CHAT_ID_2);
    }

    @Test
    @Order(5)
    void shouldUpdateTelegramChatId() {
        TelegramUserDto telegramUserDto = TelegramUserDto.builder()
                .id(2)
                .username(TELEGRAM_USERNAME_2)
                .telegramUserId(TELEGRAM_USER_ID_2)
                .telegramChatId(TELEGRAM_CHAT_ID_3)
                .build();

        telegramUserPersistenceService.update(telegramUserDto);

        Optional<TelegramUserDto> telegramUser = telegramUserPersistenceService.findByTelegramUserId(TELEGRAM_USER_ID_2);
        assertThat(telegramUser.isPresent()).isTrue();
        assertThat(telegramUser.get().id()).isEqualTo(2);
        assertThat(telegramUser.get().username()).isEqualTo(TELEGRAM_USERNAME_2);
        assertThat(telegramUser.get().telegramUserId()).isEqualTo(TELEGRAM_USER_ID_2);
        assertThat(telegramUser.get().telegramChatId()).isEqualTo(TELEGRAM_CHAT_ID_3);
    }

    @Test
    @Order(6)
    void shouldUpdateTelegramUserId() {
        TelegramUserDto telegramUserDto = TelegramUserDto.builder()
                .id(2)
                .username(TELEGRAM_USERNAME_2)
                .telegramUserId(TELEGRAM_USER_ID_3)
                .telegramChatId(TELEGRAM_CHAT_ID_3)
                .build();

        telegramUserPersistenceService.update(telegramUserDto);

        Optional<TelegramUserDto> telegramUser = telegramUserPersistenceService.findByTelegramUserId(TELEGRAM_USER_ID_3);
        assertThat(telegramUser.isPresent()).isTrue();
        assertThat(telegramUser.get().id()).isEqualTo(2);
        assertThat(telegramUser.get().username()).isEqualTo(TELEGRAM_USERNAME_2);
        assertThat(telegramUser.get().telegramUserId()).isEqualTo(TELEGRAM_USER_ID_3);
        assertThat(telegramUser.get().telegramChatId()).isEqualTo(TELEGRAM_CHAT_ID_3);
    }

    @Test
    @Order(7)
    void shouldUpdateUsername() {
        TelegramUserDto telegramUserDto = TelegramUserDto.builder()
                .id(2)
                .username(TELEGRAM_USERNAME_3)
                .telegramUserId(TELEGRAM_USER_ID_3)
                .telegramChatId(TELEGRAM_CHAT_ID_3)
                .build();

        telegramUserPersistenceService.update(telegramUserDto);

        Optional<TelegramUserDto> telegramUser = telegramUserPersistenceService.findByTelegramUserId(TELEGRAM_USER_ID_3);
        assertThat(telegramUser.isPresent()).isTrue();
        assertThat(telegramUser.get().id()).isEqualTo(2);
        assertThat(telegramUser.get().username()).isEqualTo(TELEGRAM_USERNAME_3);
        assertThat(telegramUser.get().telegramUserId()).isEqualTo(TELEGRAM_USER_ID_3);
        assertThat(telegramUser.get().telegramChatId()).isEqualTo(TELEGRAM_CHAT_ID_3);
    }

    @Test
    @Order(8)
    void shouldDeleteUser() {
        compositeUserPersistenceService.findByTelegramUserId(2)
                        .ifPresent(compositeUserRecord -> {
                            categoryPersistenceService.findByUserIdAndTitle(compositeUserRecord.getId(), CategoryConstant.DEFAULT_CATEGORY)
                                    .ifPresent(categoryDto -> categoryPersistenceService.delete(categoryDto.id()));
                            compositeUserPersistenceService.delete(compositeUserRecord.getId());
                        });

        telegramUserPersistenceService.delete(2);

        assertThatCode(() -> telegramUserPersistenceService.findById(2))
                .isExactlyInstanceOf(RuntimeException.class)
                .hasMessage("Couldn't find telegram user by id: %s".formatted(2));
    }

}
