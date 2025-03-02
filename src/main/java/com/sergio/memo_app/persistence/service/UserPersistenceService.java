package com.sergio.memo_app.persistence.service;

import com.sergio.memo_app.generated.tables.AppUser;
import com.sergio.memo_app.persistence.dto.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.sergio.memo_app.mapper.PersistenceMapper.toUserDto;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserPersistenceService implements BaseCrud<UserDto> {

    private final DSLContext dslContext;

    @Override
    public List<UserDto> findAll() {
        log.info("Searching for all users");
        return dslContext
                .select()
                .from(AppUser.APP_USER)
                .fetch(toUserDto());
    }

    @Override
    public UserDto findById(Long id) {
        log.info("Searching for a user by id [{}]", id);
        return dslContext.select()
                .from(AppUser.APP_USER)
                .where(AppUser.APP_USER.ID.eq(id.intValue()))
                .fetchOptional()
                .map(toUserDto())
                .orElseThrow(() -> new RuntimeException("Couldn't find user by id: %s".formatted(id)));
    }

    @Override
    public UserDto update(UserDto data) {
        log.info("Updating {}", data);
        int numberOfRecords = dslContext.update(AppUser.APP_USER)
                .set(AppUser.APP_USER.USERNAME, data.username())
                .set(AppUser.APP_USER.EMAIL, data.email())
                .where(AppUser.APP_USER.ID.eq(data.id()))
                .execute();
        return data;
    }

    @Override
    public UserDto insert(UserDto data) {
        log.info("Inserting {}", data);
        int numberOfRecords = dslContext.insertInto(AppUser.APP_USER)
                .set(AppUser.APP_USER.USERNAME, data.username())
                .set(AppUser.APP_USER.EMAIL, data.email())
                .execute();
        return data;
    }

    @Override
    public void delete(Long id) {
        log.info("Deleting user with id [{}]", id);
        dslContext.delete(AppUser.APP_USER)
                .where(AppUser.APP_USER.ID.eq(id.intValue()))
                .execute();
    }

    public UserDto findBy(String username) {
        log.info("Searching for a user with username [{}]", username);
        return dslContext.select()
                .from(AppUser.APP_USER)
                .where(AppUser.APP_USER.USERNAME.eq(username))
                .fetchOptional()
                .map(toUserDto())
                .orElseThrow(() -> new RuntimeException("Couldn't find user by username: %s".formatted(username)));
    }
}
