package com.sergio.memo_app.api.controller;

import com.sergio.memo_app.api.dto.CardSetDto;
import com.sergio.memo_app.persistence.service.CardSetPersistenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class CardSetController {

    private final CardSetPersistenceService cardSetPersistenceService;

    @PostMapping("/set/save")
    public CardSetDto save(@RequestBody CardSetDto cardSetDto) {
        return cardSetPersistenceService.save(cardSetDto);
    }

    @GetMapping("/set")
    public CardSetDto findByTitle(@RequestParam String title) {
        return cardSetPersistenceService.find(title);
    }
    @GetMapping("/set/{id}")
    public CardSetDto findById(@PathVariable Long id) {
        return cardSetPersistenceService.findById(id);
    }
    @GetMapping("/set-by-user")
    public List<CardSetDto> findByUserId(@RequestParam Long userId) {
        return cardSetPersistenceService.findAll(userId);
    }

}
