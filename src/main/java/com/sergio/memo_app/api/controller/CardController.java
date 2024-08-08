package com.sergio.memo_app.api.controller;

import com.sergio.memo_app.api.dto.CardDto;
import com.sergio.memo_app.persistence.service.CardPersistenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class CardController {

    private final CardPersistenceService cardPersistenceService;

    @PostMapping("/card")
    public List<CardDto> save(@RequestParam Long setId, @RequestBody List<CardDto> cardDto) {
        return cardPersistenceService.addCards(setId, cardDto);
    }

    @GetMapping("/card")
    public List<CardDto> find(@RequestParam Long cardSetId) {
        return cardPersistenceService.findAll(cardSetId);
    }

    @PutMapping("/card")
    public CardDto update(@RequestParam Long cardId, @RequestBody CardDto cardDto) {
        return cardPersistenceService.update(cardId, cardDto);
    }

}
