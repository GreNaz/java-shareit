package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.error.exception.BadRequestException;
import ru.practicum.shareit.error.exception.NotFoundException;
import ru.practicum.shareit.item.model.dto.ItemDto;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    @Override
    public List<ItemDto> get() {
        return itemRepository.get();
    }

    @Override
    public ItemDto get(long id) {
        return itemRepository.get(id).orElseThrow(() ->
                new BadRequestException("item with id = " + id + " not found"));
    }

    @Override
    public ItemDto create(long userId, ItemDto itemDto) {
        userRepository.get(userId).orElseThrow(() ->
                new NotFoundException("User with id = " + userId + " not found"));
        return itemRepository.create(userId, itemDto);
    }

    @Override
    public ItemDto update(long id, ItemDto itemDto) {
        return itemRepository.update(id, itemDto);
    }

    @Override
    public List<ItemDto> search(String text) {
        return itemRepository.search(text);
    }

    @Override
    public void delete(long id) {
        itemRepository.delete(id);
    }
}
