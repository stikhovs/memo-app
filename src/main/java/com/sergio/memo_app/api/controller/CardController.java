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

    @PostMapping("/card")
    public List<CardApiDto> save(@RequestParam Long setId, @RequestBody List<CardDto> cardDto) {
        return cardApiService.save(setId, cardDto);
    }
    @GetMapping("/card")
    public List<CardApiDto> find(@RequestParam Long cardSetId) {
        return cardApiService.find(cardSetId);
    }
    @PutMapping("/card")
    public CardApiDto update(@RequestParam Long cardId, @RequestBody CardDto cardDto) {
        return cardApiService.update(cardId, cardDto);
    }

}
