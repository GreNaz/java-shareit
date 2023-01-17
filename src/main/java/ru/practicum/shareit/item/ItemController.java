package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.error.validation.Create;
import ru.practicum.shareit.item.dto.ItemDto;

import java.util.HashMap;

/**
 * TODO Sprint add-controllers.
 */
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/items")
public class ItemController {
    private HashMap<Integer, ItemDto> items = new HashMap<>();
    private long ID = 0L;

    @PostMapping
    public ItemDto create(
            @Validated(Create.class)
            @RequestHeader(name = "X-Sharer-User-Id") long userId,
            @RequestBody ItemDto itemDto) {
        log.info("Received a request to add a item");

        return itemDto;
    }

}
