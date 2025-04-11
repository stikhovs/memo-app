package com.sergio.memo_app.component.persistence;

import com.sergio.memo_app.component.base.BaseCT;
import com.sergio.memo_app.persistence.dto.CategoryDto;
import com.sergio.memo_app.persistence.service.CategoryPersistenceService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

import static com.sergio.memo_app.util.ConstantHelper.Category.*;
import static com.sergio.memo_app.util.ConstantHelper.User.USER_ID_1;
import static org.assertj.core.api.Assertions.assertThat;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestCategoryPersistenceService extends BaseCT {

    @Autowired
    private CategoryPersistenceService categoryPersistenceService;

    @Test
    @Order(1)
    void shouldFindById() {
        CategoryDto categoryDto = categoryPersistenceService.findById(1L);

        assertThat(categoryDto).isNotNull();
        assertThat(categoryDto.id()).isEqualTo(CATEGORY_ID_1);
        assertThat(categoryDto.title()).isEqualTo(CATEGORY_TITLE_1);
        assertThat(categoryDto.userId()).isEqualTo(USER_ID_1);
    }
    @Test
    @Order(2)
    void shouldInsert() {
        CategoryDto categoryDto = categoryPersistenceService.insert(CategoryDto.builder()
                        .userId(USER_ID_1)
                        .title(CATEGORY_TITLE_2)
                .build());

        assertThat(categoryDto).isNotNull();
        assertThat(categoryDto.id()).isEqualTo(CATEGORY_ID_2);
        assertThat(categoryDto.title()).isEqualTo(CATEGORY_TITLE_2);
        assertThat(categoryDto.userId()).isEqualTo(USER_ID_1);
    }
    @Test
    @Order(3)
    void shouldUpdate() {
        CategoryDto categoryDto = categoryPersistenceService.update(CategoryDto.builder()
                        .id(CATEGORY_ID_2)
                        .userId(USER_ID_1)
                        .title(CATEGORY_TITLE_2.toUpperCase())
                .build());

        assertThat(categoryDto).isNotNull();
        assertThat(categoryDto.id()).isEqualTo(CATEGORY_ID_2);
        assertThat(categoryDto.title()).isEqualTo(CATEGORY_TITLE_2.toUpperCase());
        assertThat(categoryDto.userId()).isEqualTo(USER_ID_1);
    }
    @Test
    @Order(4)
    void shouldFindAll() {
        List<CategoryDto> result = categoryPersistenceService.findAll();

        assertThat(result).hasSize(2);
        assertThat(result.getFirst().id()).isEqualTo(CATEGORY_ID_1);
        assertThat(result.getFirst().title()).isEqualTo(CATEGORY_TITLE_1);
        assertThat(result.getFirst().userId()).isEqualTo(USER_ID_1);

        assertThat(result.getLast().id()).isEqualTo(CATEGORY_ID_2);
        assertThat(result.getLast().title()).isEqualTo(CATEGORY_TITLE_2.toUpperCase());
        assertThat(result.getLast().userId()).isEqualTo(USER_ID_1);
    }
    @Test
    @Order(5)
    void shouldFindAllByUserId() {
        List<CategoryDto> result = categoryPersistenceService.findAllByUserId(USER_ID_1);

        assertThat(result).hasSize(2);
        assertThat(result.getFirst().id()).isEqualTo(CATEGORY_ID_1);
        assertThat(result.getFirst().title()).isEqualTo(CATEGORY_TITLE_1);
        assertThat(result.getFirst().userId()).isEqualTo(USER_ID_1);

        assertThat(result.getLast().id()).isEqualTo(CATEGORY_ID_2);
        assertThat(result.getLast().title()).isEqualTo(CATEGORY_TITLE_2.toUpperCase());
        assertThat(result.getLast().userId()).isEqualTo(USER_ID_1);
    }
    @Test
    @Order(6)
    void shouldFindByUserIdAndTitle() {
        Optional<CategoryDto> result = categoryPersistenceService.findByUserIdAndTitle(USER_ID_1, CATEGORY_TITLE_1);

        assertThat(result).isPresent();
        assertThat(result.get().id()).isEqualTo(CATEGORY_ID_1);
        assertThat(result.get().title()).isEqualTo(CATEGORY_TITLE_1);
        assertThat(result.get().userId()).isEqualTo(USER_ID_1);
    }
    @Test
    @Order(7)
    void shouldGetByUserIdAndTitle() {
        CategoryDto result = categoryPersistenceService.getByUserIdAndTitle(USER_ID_1, CATEGORY_TITLE_1);

        assertThat(result).isNotNull();
        assertThat(result.id()).isEqualTo(CATEGORY_ID_1);
        assertThat(result.title()).isEqualTo(CATEGORY_TITLE_1);
        assertThat(result.userId()).isEqualTo(USER_ID_1);
    }
    @Test
    @Order(8)
    void shouldFindGetByTelegramChatIdAndTitle() {
        Optional<CategoryDto> result = categoryPersistenceService.findByTelegramChatIdAndTitle(456L, CATEGORY_TITLE_1);

        assertThat(result).isPresent();
        assertThat(result.get().id()).isEqualTo(CATEGORY_ID_1);
        assertThat(result.get().title()).isEqualTo(CATEGORY_TITLE_1);
        assertThat(result.get().userId()).isEqualTo(USER_ID_1);
    }
    @Test
    @Order(9)
    void shouldGetByTelegramChatIdAndTitle() {
        CategoryDto result = categoryPersistenceService.getByTelegramChatIdAndTitle(456L, CATEGORY_TITLE_1);

        assertThat(result).isNotNull();
        assertThat(result.id()).isEqualTo(CATEGORY_ID_1);
        assertThat(result.title()).isEqualTo(CATEGORY_TITLE_1);
        assertThat(result.userId()).isEqualTo(USER_ID_1);
    }
    @Test
    @Order(10)
    void shouldDelete() {
        categoryPersistenceService.delete(CATEGORY_ID_2);

        Optional<CategoryDto> result = categoryPersistenceService.findByTelegramChatIdAndTitle(456L, CATEGORY_TITLE_2.toUpperCase());
        assertThat(result).isEmpty();
    }

}
