package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.model.dto.ItemDto;

import java.util.List;

public interface ItemService {
    List<ItemDto> getUserItems(long userId);

    ItemDto getItem(long id);

    ItemDto create(long userId, ItemDto itemDto);

    ItemDto update(long id, ItemDto itemDto, long ownerId);

    List<ItemDto> search(String text);

    void delete(long id);
}
