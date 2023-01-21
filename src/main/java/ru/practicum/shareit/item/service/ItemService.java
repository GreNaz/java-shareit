package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.model.dto.ItemDto;

import java.util.List;

public interface ItemService {
    List<ItemDto> get();

    ItemDto get(long id);

    ItemDto create(long userId, ItemDto itemDto);

    ItemDto update(long id, ItemDto itemDto);

    List<ItemDto> search(String text);

    void delete(long id);
}
