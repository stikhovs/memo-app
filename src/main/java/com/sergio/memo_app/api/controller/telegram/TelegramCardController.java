package com.sergio.memo_app.api.controller.telegram;

import com.sergio.memo_app.api.dto.CardApiDto;
import com.sergio.memo_app.api.service.CardApiService;
import com.sergio.memo_app.persistence.dto.CardDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TelegramCardController {

    private final CardApiService cardApiService;

    @GetMapping("/telegram/get-cards")
    List<CardApiDto> getCards(@RequestParam Long cardSetId) {
        return cardApiService.find(cardSetId);
    }
    @PutMapping("/telegram/card/update")
    public CardApiDto update(@RequestBody CardDto cardDto) {
        return cardApiService.update(cardDto.id(), cardDto);
    }
}
