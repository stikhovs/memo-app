package com.sergio.memo_app.api.controller.telegram;

import com.sergio.memo_app.api.service.CategoryApiService;
import com.sergio.memo_app.persistence.dto.CategoryDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class TelegramCategoryController {

    private final CategoryApiService categoryApiService;

    @PostMapping("/telegram/category/save")
    public CategoryDto save(@RequestParam Long chatId, @RequestBody CategoryDto categoryDto) {
        return categoryApiService.saveFromTelegram(chatId, categoryDto);
    }

    @PutMapping("/telegram/category/update")
    public CategoryDto update(@RequestBody CategoryDto categoryDto) {
        return categoryApiService.update(categoryDto);
    }

    @GetMapping("/telegram/category")
    public CategoryDto getById(@RequestParam Long categoryId) {
        return categoryApiService.getById(categoryId);
    }

    @GetMapping("/telegram/category/by-chat")
    public List<CategoryDto> getByChatId(@RequestParam Long chatId) {
        return categoryApiService.findAllByChatId(chatId);
    }

    @DeleteMapping("/telegram/category/delete")
    public void delete(@RequestParam Long categoryId) {
        categoryApiService.delete(categoryId);
    }
}
