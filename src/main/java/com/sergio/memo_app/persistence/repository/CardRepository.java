package com.sergio.memo_app.persistence.repository;

import com.sergio.memo_app.persistence.entity.Card;

import java.util.List;

public interface CardRepository /*extends ListCrudRepository<Card, Long>, ListPagingAndSortingRepository<Card, Long>*/ {

    List<Card> findAllByCardSetId(Long cardSetId);

}
