package com.sergio.memo_app.api.controller;

import com.sergio.memo_app.api.dto.CardApiDto;
import com.sergio.memo_app.api.service.CardApiService;
import com.sergio.memo_app.persistence.dto.CardDto;
import com.sergio.memo_app.persistence.service.CardPersistenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class CardController {

    private final CardPersistenceService cardPersistenceService;
    private final CardApiService cardApiService;

    @PostMapping("/api/card")
    public List<CardApiDto> save(@RequestParam Long setId, @RequestBody List<CardDto> cards) {
        return cardApiService.save(setId, cards);
    }
    @GetMapping("/api/card")
    public List<CardApiDto> find(@RequestParam Long cardSetId) {
        return cardApiService.find(cardSetId);
    }
    @PutMapping("/api/card")
    public CardApiDto update(@RequestParam Long cardId, @RequestBody CardDto cardDto) {
        return cardApiService.update(cardId, cardDto);
    }

}
