package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.model.dto.CommentDto;
import ru.practicum.shareit.item.model.dto.ItemDto;
import ru.practicum.shareit.item.model.dto.ItemDtoBooking;

import java.util.List;

public interface ItemService {
    List<ItemDtoBooking> getUserItems(long userId);

    ItemDtoBooking getById(long itemId, long userId);

    ItemDto create(long userId, Item item);

    ItemDto update(long id, Item item, long ownerId);

    CommentDto createComment(long itemId, CommentDto commentDto, long authorId);

    List<ItemDto> search(String text);

    void delete(long id);
}
