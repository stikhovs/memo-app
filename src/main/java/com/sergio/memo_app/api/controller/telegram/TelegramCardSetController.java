package com.sergio.memo_app.api.controller.telegram;

import com.sergio.memo_app.api.dto.CardSetApiDto;
import com.sergio.memo_app.api.service.CardSetApiService;
import com.sergio.memo_app.persistence.dto.CardSetDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TelegramCardSetController {

    private final CardSetApiService cardSetApiService;

    @GetMapping("/telegram/sets-by-chat")
    public List<CardSetApiDto> findByTelegramChatId(@RequestParam Long telegramChatId) {
        return cardSetApiService.findByTelegramChatId(telegramChatId);
    }
    @GetMapping("/telegram/set-and-cards-by-set-id")
    public CardSetApiDto findBySetId(@RequestParam Long cardSetId) {
        return cardSetApiService.findBySetId(cardSetId);
    }

    @PostMapping("/telegram/set/save")
    public CardSetApiDto save(@RequestBody CardSetDto cardSetDto) {
        return cardSetApiService.save(cardSetDto);
    }

    @PutMapping("/telegram/set/update")
    public CardSetApiDto update(@RequestBody CardSetDto cardSetDto) {
        return cardSetApiService.update(cardSetDto);
    }
}
