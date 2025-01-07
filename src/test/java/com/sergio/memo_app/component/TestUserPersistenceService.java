package com.sergio.memo_app.component;

import com.sergio.memo_app.component.base.BaseCT;
import com.sergio.memo_app.persistence.dto.UserDto;
import com.sergio.memo_app.persistence.service.UserPersistenceService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.sergio.memo_app.ConstantHelper.User.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestUserPersistenceService extends BaseCT {

    @Autowired
    private UserPersistenceService userPersistenceService;

    @Test
    @Order(1)
    void shouldFindAllUsers() {
        List<UserDto> users = userPersistenceService.findAll();

        assertThat(users).hasSize(1);
        assertThat(users.getFirst()).isNotNull();
        assertThat(users.getFirst().id()).isEqualTo(USER_ID_1);
        assertThat(users.getFirst().email()).isEqualTo(EMAIL_1);
        assertThat(users.getFirst().username()).isEqualTo(USERNAME_1);
    }

    @Test
    @Order(2)
    void shouldFindUserById() {
        UserDto userDto = userPersistenceService.findById(1L);

        assertThat(userDto).isNotNull();
        assertThat(userDto.id()).isEqualTo(USER_ID_1);
        assertThat(userDto.email()).isEqualTo(EMAIL_1);
        assertThat(userDto.username()).isEqualTo(USERNAME_1);
    }

    @Test
    @Order(3)
    void shouldFindUserByUsername() {
        UserDto userDto = userPersistenceService.findBy(USERNAME_1);

        assertThat(userDto).isNotNull();
        assertThat(userDto.id()).isEqualTo(USER_ID_1);
        assertThat(userDto.email()).isEqualTo(EMAIL_1);
        assertThat(userDto.username()).isEqualTo(USERNAME_1);
    }

    @Test
    @Order(4)
    void shouldInsertUser() {
        UserDto data = UserDto.builder()
                .username(USERNAME_2)
                .email(EMAIL_2)
                .build();

        userPersistenceService.insert(data);

        UserDto userDto = userPersistenceService.findBy(USERNAME_2);
        assertThat(userDto).isNotNull();
        assertThat(userDto.id()).isEqualTo(USER_ID_2);
        assertThat(userDto.email()).isEqualTo(EMAIL_2);
        assertThat(userDto.username()).isEqualTo(USERNAME_2);
    }

    @Test
    @Order(5)
    void shouldUpdateUsername() {
        UserDto data = UserDto.builder()
                .id(USER_ID_2)
                .username(USERNAME_3)
                .email(EMAIL_2)
                .build();

        userPersistenceService.update(data);

        UserDto userDto = userPersistenceService.findBy(USERNAME_3);
        assertThat(userDto).isNotNull();
        assertThat(userDto.id()).isEqualTo(USER_ID_2);
        assertThat(userDto.email()).isEqualTo(EMAIL_2);
        assertThat(userDto.username()).isEqualTo(USERNAME_3);
    }

    @Test
    @Order(6)
    void shouldUpdateEmail() {
        UserDto data = UserDto.builder()
                .id(USER_ID_2)
                .username(USERNAME_3)
                .email(EMAIL_3)
                .build();

        userPersistenceService.update(data);

        UserDto userDto = userPersistenceService.findBy(USERNAME_3);
        assertThat(userDto).isNotNull();
        assertThat(userDto.id()).isEqualTo(USER_ID_2);
        assertThat(userDto.email()).isEqualTo(EMAIL_3);
        assertThat(userDto.username()).isEqualTo(USERNAME_3);
    }

    @Test
    @Order(7)
    void shouldDeleteUser() {
        userPersistenceService.delete(USER_ID_2.longValue());

        assertThatCode(() -> userPersistenceService.findById(USER_ID_2.longValue()))
                .isExactlyInstanceOf(RuntimeException.class)
                .hasMessage("Couldn't find user by id: %s".formatted(USER_ID_2));
    }

}
