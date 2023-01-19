package ru.practicum.shareit.item.repository;

import ru.practicum.shareit.item.model.dto.ItemDto;

import java.util.List;
import java.util.Optional;

public interface ItemRepository {
    List<ItemDto> get();

    Optional<ItemDto> get(long id);

    ItemDto create(ItemDto itemDto);

    ItemDto update(long id, ItemDto itemDto);

    void delete(long id);
}
