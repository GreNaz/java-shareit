package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.error.exception.NotFoundException;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.model.ItemMapper;
import ru.practicum.shareit.item.model.dto.ItemDto;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    @Override
    public List<ItemDto> getUserItems(long userId) {
        //get all user items
        List<Item> userItems = itemRepository.get().stream()
                .filter(item -> item.owner > 0)
                .filter(item -> item.getOwner() == userId)
                .collect(Collectors.toList());

        return userItems.stream()
                .map(ItemMapper::toItemDto).collect(Collectors.toList());
    }

    @Override
    public ItemDto getItem(long id) {
        return itemRepository.get(id).orElseThrow(() ->
                new NotFoundException("Item with id = " + id + " not found"));
    }

    @Override
    public ItemDto create(long userId, ItemDto itemDto) {
        userRepository.get(userId).orElseThrow(() ->
                new NotFoundException("User with id = " + userId + " not found"));
        return itemRepository.create(userId, itemDto);
    }

    @Override
    public ItemDto update(long id, ItemDto itemDto, long ownerId) {
        //get all user items id
        List<Long> userItems = itemRepository.get().stream()
                .filter(item -> item.owner > 0)
                .filter(item -> item.getOwner() == ownerId)
                .map(Item::getId)
                .collect(Collectors.toList());
        //ownership check
        if (userItems.contains(id)) {
            return itemRepository.update(id, itemDto, ownerId);
        } else
            throw new NotFoundException("It is not possible to edit this item on behalf of the user with id = " + ownerId);
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
