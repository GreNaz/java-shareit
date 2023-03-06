package ru.practicum.shareit.request.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.error.validation.Create;
import ru.practicum.shareit.request.model.dto.ItemRequestDtoRQ;
import ru.practicum.shareit.request.model.dto.ItemRequestDtoRS;
import ru.practicum.shareit.request.service.ItemRequestService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/requests")
public class ItemRequestController {
    private final ItemRequestService itemRequestService;

    @PostMapping
    public ItemRequestDtoRS create(
            @RequestHeader("X-Sharer-User-Id") long userId,
            @Validated(Create.class) @RequestBody ItemRequestDtoRQ itemRequestDtoRQ) {
        return itemRequestService.create(userId, itemRequestDtoRQ);
    }

    @GetMapping
    public List<ItemRequestDtoRS> getRequestsInfo(
            @RequestHeader("X-Sharer-User-Id") long userId) {
        return itemRequestService.getInfo(userId);
    }

    @GetMapping("/{requestId}")
    public ItemRequestDtoRS getRequestInfo(
            @RequestHeader("X-Sharer-User-Id") long userId,
            @PathVariable long requestId) {
        return itemRequestService.getInfo(userId, requestId);
    }

    @GetMapping("/all")
    public List<ItemRequestDtoRS> getRequestsList(
            @RequestHeader("X-Sharer-User-Id") long userId,
            @PositiveOrZero @RequestParam(defaultValue = "0") int from,
            @Positive @RequestParam(defaultValue = "10") int size) {
        return itemRequestService.getRequests(userId, from, size);
    }
}
