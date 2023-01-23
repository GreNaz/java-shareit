package ru.practicum.shareit.item.repository;

import ru.practicum.shareit.item.model.Item;

import java.util.List;
import java.util.Optional;

public interface ItemRepository {
    List<Item> get();

    Optional<Item> get(long id);

    Item create(long userId, Item item);

    Item update(long id, Item item, long ownerId);

    void delete(long id);

    List<Item> search(String text);

}
