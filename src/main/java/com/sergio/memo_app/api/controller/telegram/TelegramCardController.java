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
    public List<CardApiDto> getCards(@RequestParam Long cardSetId) {
        return cardApiService.find(cardSetId);
    }
    @PutMapping("/telegram/card/update")
    public CardApiDto update(@RequestBody CardDto cardDto) {
        return cardApiService.update(cardDto.id(), cardDto);
    }
    @PostMapping("/telegram/card/add")
    public CardApiDto add(@RequestParam Long cardSetId, @RequestBody CardDto cardDto) {
        return cardApiService.add(cardSetId, cardDto);
    }
    @PostMapping("/add-batch")
    public List<CardApiDto> addBatch(@RequestParam Long cardSetId, @RequestBody List<CardDto> cards) {
        return cardApiService.save(cardSetId, cards);
    }
    @DeleteMapping("/telegram/card/delete")
    public void delete(@RequestParam Long cardId) {
        cardApiService.deleteByCardId(cardId);
    }

}
