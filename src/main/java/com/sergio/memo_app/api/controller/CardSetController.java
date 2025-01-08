package com.sergio.memo_app.api.controller;

import com.sergio.memo_app.api.dto.CardSetApiDto;
import com.sergio.memo_app.api.service.CardSetApiService;
import com.sergio.memo_app.persistence.dto.CardSetDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class CardSetController {

    private final CardSetApiService cardSetApiService;

    @PostMapping("/set/save")
    public CardSetApiDto save(@RequestBody CardSetDto cardSetDto) {
        return cardSetApiService.save(cardSetDto);
    }

    @GetMapping("/set")
    public CardSetApiDto findByTitle(@RequestParam String title) {
        return cardSetApiService.findByTitle(title);
    }
    @GetMapping("/set/{id}")
    public CardSetApiDto findById(@PathVariable Long id) {
        return cardSetApiService.findById(id);
    }
    @GetMapping("/set-by-user")
    public List<CardSetApiDto> findByUserId(@RequestParam Long userId) {
        return cardSetApiService.findByUserId(userId);
    }
    @GetMapping("/sets")
    public List<CardSetApiDto> getSets(@RequestParam Long userId, @RequestParam List<Long> ids) {
        return cardSetApiService.getSets(userId, ids);
    }
    @GetMapping("/titles-and-ids")
    public List<CardSetApiDto> getTitlesAndIds(@RequestParam Long userId) {
        return cardSetApiService.getTitlesAndIds(userId);
    }

}
