package ru.practicum.shareit.item.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.error.validation.Create;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.service.ItemService;

import java.util.List;

/**
 * TODO Sprint add-controllers.
 */
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/items")
public class ItemController {
    private final ItemService itemService;

    @PostMapping
    public ItemDto create(
            @RequestHeader(name = "X-Sharer-User-Id") long userId,
            @Validated(Create.class) @RequestBody ItemDto itemDto) {
        log.info("Received a request to add a item");
        return itemService.create(userId, itemDto);
    }

    @PatchMapping("/{itemId}")
    public ItemDto update(
            @PathVariable long itemId,
            @RequestBody ItemDto itemDto,
            @RequestHeader(name = "X-Sharer-User-Id") long ownerId) {
        log.info("Received a request to update the item");
        return itemService.update(itemId, itemDto, ownerId);
    }

    @GetMapping("{itemId}")
    public ItemDto getItem(
            @PathVariable long itemId) {
        log.info("Received a request to get Item with id {}", itemId);
        return itemService.getItem(itemId);
    }

    @GetMapping
    public List<ItemDto> getUserItems(
            @RequestHeader(name = "X-Sharer-User-Id") long ownerId) {
        log.info("Received a request to get Items from User with id = {}", ownerId);
        return itemService.getUserItems(ownerId);
    }

    @GetMapping("/search")
    public List<ItemDto> search(
            @RequestHeader(name = "X-Sharer-User-Id") long ownerId,
            @RequestParam(required = false) String text) {
        log.info("Received a request to search by query = {}", text);
        return itemService.search(text);
    }

    @DeleteMapping("/{itemId}")
    public void delete(
            @PathVariable long itemId) {
        log.info("Received a request to delete Item with id {} ", itemId);
        itemService.delete(itemId);
    }
}
