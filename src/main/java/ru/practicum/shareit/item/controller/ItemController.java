package ru.practicum.shareit.item.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.error.validation.Create;
import ru.practicum.shareit.item.model.dto.CommentDto;
import ru.practicum.shareit.item.model.dto.ItemDto;
import ru.practicum.shareit.item.model.dto.ItemDtoBooking;
import ru.practicum.shareit.item.service.ItemService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.Collections;
import java.util.List;

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

    @PostMapping("/{itemId}/comment")
    public CommentDto createComment(
            @PathVariable long itemId,
            @RequestBody @Validated(Create.class) CommentDto commentDto,
            @RequestHeader(name = "X-Sharer-User-Id") long authorId) {
        log.info("Received a request to update the item");
        return itemService.createComment(itemId, commentDto, authorId);
    }

    @GetMapping
    public List<ItemDtoBooking> getUserItems(
            @RequestHeader(name = "X-Sharer-User-Id") long ownerId,
            @PositiveOrZero @RequestParam(defaultValue = "0") int from,
            @Positive @RequestParam(defaultValue = "20") int size) {
        PageRequest pageable = PageRequest.of(from / size, size);
        log.info("Received a request to get Items from User with id = {}", ownerId);
        return itemService.getUserItems(ownerId, pageable);
    }

    @GetMapping("/{itemId}")
    public ItemDtoBooking getItem(
            @RequestHeader(name = "X-Sharer-User-Id") long ownerId,
            @PathVariable long itemId) {
        log.info("Received a request to get Item with id {}", itemId);
        return itemService.getById(itemId, ownerId);
    }

    @GetMapping("/search")
    public List<ItemDto> search(
            @RequestHeader(name = "X-Sharer-User-Id") long ownerId,
            @RequestParam String text,
            @PositiveOrZero @RequestParam(defaultValue = "0") int from,
            @Positive @RequestParam(defaultValue = "20") int size) {
        PageRequest pageRequest = PageRequest.of(from / size, size);
        log.info("Received a request to search by query = {}", text);
        if (text.isBlank()) {
            return Collections.emptyList();
        } else {
            return itemService.search(text, pageRequest);
        }
    }

    @DeleteMapping("/{itemId}")
    public void delete(
            @PathVariable long itemId) {
        log.info("Received a request to delete Item with id {} ", itemId);
        itemService.delete(itemId);
    }
}
