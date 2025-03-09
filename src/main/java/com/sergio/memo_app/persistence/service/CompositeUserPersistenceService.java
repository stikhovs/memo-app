package com.sergio.memo_app.persistence.service;

import com.sergio.memo_app.generated.tables.CompositeUser;
import com.sergio.memo_app.generated.tables.records.CompositeUserRecord;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CompositeUserPersistenceService implements BaseCrud<CompositeUserRecord, Integer>{

    private final DSLContext dslContext;

    @Override
    public List<CompositeUserRecord> findAll() {
        log.info("Searching for all composite users");
        return dslContext.selectFrom(CompositeUser.COMPOSITE_USER)
                .collect(Collectors.toList());
    }

    @Override
    public CompositeUserRecord findById(Integer id) {
        log.info("Searching for a composite user by id [{}]", id);
        return dslContext.selectFrom(CompositeUser.COMPOSITE_USER)
                .where(CompositeUser.COMPOSITE_USER.ID.eq(id))
                .fetchOptional()
                .orElseThrow(() -> new RuntimeException("Couldn't find composite user by id: %s".formatted(id)));
    }

    public Optional<CompositeUserRecord> findByAppUserId(Integer id) {
        log.info("Searching for a composite user by app_user_id [{}]", id);
        return dslContext.selectFrom(CompositeUser.COMPOSITE_USER)
                .where(CompositeUser.COMPOSITE_USER.APP_USER_ID.eq(id))
                .fetchOptional();
    }

    public  Optional<CompositeUserRecord> findByTelegramUserId(Integer id) {
        log.info("Searching for a composite user by telegram_user_id [{}]", id);
        return dslContext.selectFrom(CompositeUser.COMPOSITE_USER)
                .where(CompositeUser.COMPOSITE_USER.TELEGRAM_USER_ID.eq(id))
                .fetchOptional();
    }

    @Override
    public CompositeUserRecord update(CompositeUserRecord data) {
        log.info("Updating {}", data);
        int numberOfRecords = dslContext.update(CompositeUser.COMPOSITE_USER)
                .set(CompositeUser.COMPOSITE_USER.TELEGRAM_USER_ID, data.getTelegramUserId())
                .set(CompositeUser.COMPOSITE_USER.APP_USER_ID, data.getAppUserId())
                .where(CompositeUser.COMPOSITE_USER.ID.eq(data.getId()))
                .execute();
        return data;
    }

    @Override
    public CompositeUserRecord insert(CompositeUserRecord data) {
        log.info("Inserting {}", data);
        int execute = dslContext.insertInto(CompositeUser.COMPOSITE_USER)
                .set(CompositeUser.COMPOSITE_USER.TELEGRAM_USER_ID, data.getTelegramUserId())
                .set(CompositeUser.COMPOSITE_USER.APP_USER_ID, data.getAppUserId())
                .execute();
        return data;
    }

    @Override
    public void delete(Integer id) {
        log.info("Deleting composite user with id [{}]", id);
        dslContext.delete(CompositeUser.COMPOSITE_USER)
                .where(CompositeUser.COMPOSITE_USER.ID.eq(id))
                .execute();
    }
}
