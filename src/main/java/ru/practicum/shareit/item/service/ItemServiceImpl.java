package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.error.exception.NotFoundException;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.model.ItemMapper;
import ru.practicum.shareit.item.model.dto.ItemDto;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.repository.UserRepository;

import java.util.Collections;
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
                .filter(item -> item.getOwner() == userId)
                .collect(Collectors.toList());

        return userItems.stream()
                .map(ItemMapper::toItemDto).collect(Collectors.toList());
    }

    @Override
    public ItemDto getItem(long id) {
        Item item = itemRepository.get(id).orElseThrow(() ->
                new NotFoundException("Item with id = " + id + " not found"));
        return ItemMapper.toItemDto(item);
    }

    @Override
    public ItemDto create(long userId, Item item) {
        userRepository.get(userId).orElseThrow(() ->
                new NotFoundException("User with id = " + userId + " not found"));
        return ItemMapper.toItemDto(itemRepository.create(userId, item));
    }

    @Override
    public ItemDto update(long id, Item item, long ownerId) {
        //get all user items id
        List<Long> userItems = itemRepository.get().stream()
                .filter(userItem -> userItem.getOwner() == ownerId)
                .map(Item::getId)
                .collect(Collectors.toList());
        //ownership check
        if (userItems.contains(id)) {
            return ItemMapper.toItemDto(itemRepository.update(id, item, ownerId));
        } else
            throw new NotFoundException("It is not possible to edit this item on behalf of the user with id = " + ownerId);
    }

    @Override
    public List<ItemDto> search(String text) {
        if (text.trim().isEmpty()) {
            return Collections.emptyList();
        } else {
            return itemRepository.get().stream()
                    .filter(item -> item.getName().toLowerCase().contains(text.toLowerCase()) ||
                            item.getDescription().toLowerCase().contains(text.toLowerCase()))
                    .filter(Item::getAvailable)
                    .map(ItemMapper::toItemDto)
                    .collect(Collectors.toList());
        }
    }

    @Override
    public void delete(long id) {
        itemRepository.delete(id);
    }
}
