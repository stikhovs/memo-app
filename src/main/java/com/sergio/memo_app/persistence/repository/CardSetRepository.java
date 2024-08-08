package com.sergio.memo_app.persistence.repository;

import com.sergio.memo_app.persistence.entity.CardSet;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.ListPagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface CardSetRepository extends ListCrudRepository<CardSet, Long>, ListPagingAndSortingRepository<CardSet, Long> {

    Optional<CardSet> findByTitle(String title);

    List<CardSet> findByUserId(Long userId);

//    Optional<CardSet> findById(Long id);

}
