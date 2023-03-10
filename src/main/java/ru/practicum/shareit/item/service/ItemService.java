package ru.practicum.shareit.item.service;

import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.item.model.dto.CommentDto;
import ru.practicum.shareit.item.model.dto.ItemDto;
import ru.practicum.shareit.item.model.dto.ItemDtoBooking;

import java.util.List;

@Transactional(readOnly = true)
public interface ItemService {
    List<ItemDtoBooking> getUserItems(long userId, Pageable pageable);

    ItemDtoBooking getById(long itemId, long userId);

    @Transactional
    ItemDto create(long userId, ItemDto itemDto);

    @Transactional
    ItemDto update(long itemId, ItemDto itemDto, long ownerId);

    @Transactional
    CommentDto createComment(long itemId, CommentDto commentDto, long authorId);

    List<ItemDto> search(String text, Pageable pageable);

}
