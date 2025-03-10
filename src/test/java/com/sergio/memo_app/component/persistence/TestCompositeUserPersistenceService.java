package com.sergio.memo_app.component.persistence;

import com.sergio.memo_app.component.base.BaseCT;
import com.sergio.memo_app.generated.tables.records.CompositeUserRecord;
import com.sergio.memo_app.persistence.service.CompositeUserPersistenceService;
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
public class TestCompositeUserPersistenceService extends BaseCT {

    @Autowired
    private CompositeUserPersistenceService compositeUserPersistenceService;

    @Test
    @Order(1)
    public void shouldFindAllCompositeUsers() {
        List<CompositeUserRecord> users = compositeUserPersistenceService.findAll();

        assertThat(users).hasSize(1);
        assertThat(users.getFirst()).isNotNull();
        assertThat(users.getFirst().getId()).isEqualTo(1);
        assertThat(users.getFirst().getTelegramUserId()).isEqualTo(1);
        assertThat(users.getFirst().getAppUserId()).isEqualTo(1);
    }

    @Test
    @Order(2)
    void shouldFindUserById() {
        CompositeUserRecord user = compositeUserPersistenceService.findById(1);

        assertThat(user).isNotNull();
        assertThat(user.getId()).isEqualTo(1);
        assertThat(user.getTelegramUserId()).isEqualTo(1);
        assertThat(user.getAppUserId()).isEqualTo(1);
    }

    @Test
    @Order(3)
    void shouldFindUserByAppUserId() {
        Optional<CompositeUserRecord> result = compositeUserPersistenceService.findByAppUserId(1);

        assertThat(result.isPresent()).isTrue();
        CompositeUserRecord user = result.get();
        assertThat(user.getId()).isEqualTo(1);
        assertThat(user.getTelegramUserId()).isEqualTo(1);
        assertThat(user.getAppUserId()).isEqualTo(1);
    }

    @Test
    @Order(4)
    void shouldFindUserByTelegramUserId() {
        Optional<CompositeUserRecord> result = compositeUserPersistenceService.findByTelegramUserId(1);

        assertThat(result.isPresent()).isTrue();
        CompositeUserRecord user = result.get();
        assertThat(user.getId()).isEqualTo(1);
        assertThat(user.getTelegramUserId()).isEqualTo(1);
        assertThat(user.getAppUserId()).isEqualTo(1);
    }

    @Test
    @Order(5)
    void shouldInsertUser() {
        CompositeUserRecord compositeUserRecord = new CompositeUserRecord();

        compositeUserPersistenceService.insert(compositeUserRecord);

        CompositeUserRecord user = compositeUserPersistenceService.findById(2);

        assertThat(user).isNotNull();
        assertThat(user.getId()).isEqualTo(2);
        assertThat(user.getTelegramUserId()).isNull();
        assertThat(user.getAppUserId()).isNull();
    }

    @Test
    @Order(6)
    void shouldUpdateAppUserId() {
        CompositeUserRecord toBeUpdated = compositeUserPersistenceService.findById(1);
        toBeUpdated.setAppUserId(null);

        compositeUserPersistenceService.update(toBeUpdated);

        CompositeUserRecord user = compositeUserPersistenceService.findById(1);

        assertThat(user).isNotNull();
        assertThat(user.getId()).isEqualTo(1);
        assertThat(user.getTelegramUserId()).isEqualTo(1);
        assertThat(user.getAppUserId()).isNull();
    }

    @Test
    @Order(7)
    void shouldUpdateTelegramUserId() {
        CompositeUserRecord toBeUpdated = compositeUserPersistenceService.findById(1);
        toBeUpdated.setTelegramUserId(null);

        compositeUserPersistenceService.update(toBeUpdated);

        CompositeUserRecord user = compositeUserPersistenceService.findById(1);

        assertThat(user).isNotNull();
        assertThat(user.getId()).isEqualTo(1);
        assertThat(user.getTelegramUserId()).isNull();
        assertThat(user.getAppUserId()).isNull();
    }

    @Test
    @Order(8)
    void shouldDeleteUser() {
        compositeUserPersistenceService.delete(2);

        assertThatCode(() -> compositeUserPersistenceService.findById(2))
                .isExactlyInstanceOf(RuntimeException.class)
                .hasMessage("Couldn't find composite user by id: %s".formatted(2));
    }

}
