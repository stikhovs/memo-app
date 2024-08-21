package com.sergio.memo_app.persistence.repository;

import com.sergio.memo_app.persistence.entity.CardSet;
import com.sergio.memo_app.persistence.repository.projection.TitleAndId;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.ListPagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface CardSetRepository extends ListCrudRepository<CardSet, Long>, ListPagingAndSortingRepository<CardSet, Long> {

    Optional<CardSet> findByTitle(String title);

    List<CardSet> findByUserId(Long userId);

    List<CardSet> findByUserIdAndIdIn(Long userId, List<Long> ids);

    @Query("""
            SELECT cs.title, cs.id
            FROM card_set cs
            WHERE cs.user_id = :userId
            """)
    List<TitleAndId> getByUserId(Long userId);

}
