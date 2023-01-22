package ru.practicum.shareit.item.repository;

import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.model.dto.ItemDto;

import java.util.List;
import java.util.Optional;

public interface ItemRepository {
    List<Item> get();

    Optional<ItemDto> get(long id);

    ItemDto create(long userId, ItemDto itemDto);

    ItemDto update(long id, ItemDto itemDto, long ownerId);

    void delete(long id);
}
