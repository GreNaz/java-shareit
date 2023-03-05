package ru.practicum.shareit.request.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.model.dto.ItemDtoBooking;
import ru.practicum.shareit.request.model.dto.ItemRequestDto;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/requests")
public class ItemRequestController {
    @PostMapping
    public ItemRequestDto create(ItemRequestDto itemRequestDto) {
        return null;
    }

    @GetMapping
    public List<ItemRequestDto> get() {
        return null;
    }

    @GetMapping("/all")
    public ItemRequestDto getAll(
            @RequestParam long from,
            @RequestParam long size) {
        return null;
    }

    @GetMapping("/{requestId}")
    public ItemDtoBooking getRequest(
            @PathVariable long requestId) {
        log.info("Received a request to getRequest with id {}", requestId);
        return null;
    }
}
