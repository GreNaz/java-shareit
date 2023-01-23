package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.model.dto.ItemDto;

import java.util.List;

public interface ItemService {
    List<ItemDto> getUserItems(long userId);

    ItemDto getItem(long id);

    ItemDto create(long userId, Item item);

    ItemDto update(long id, Item item, long ownerId);

    List<ItemDto> search(String text);

    void delete(long id);
}
