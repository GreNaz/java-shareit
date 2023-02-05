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
        List<Item> userItems = itemRepository.findAll().stream()
                .filter(item -> item.getOwner().getId() == userId)
                .collect(Collectors.toList());

        return userItems.stream().map(ItemMapper::toItemDto).collect(Collectors.toList());
    }

    @Override
    public ItemDto getItem(long id) {
        Item item = itemRepository.findById(id).orElseThrow(()
                -> new NotFoundException("Item with id = " + id + " not found"));
        return ItemMapper.toItemDto(item);
    }

    @Override
    public ItemDto create(long userId, Item item) {
        item.setOwner(userRepository.findById(userId).orElseThrow(()
                -> new NotFoundException("User with id = " + userId + " not found")));

        return ItemMapper.toItemDto(itemRepository.save(item));
    }

    @Override
    public ItemDto update(long id, Item item, long ownerId) {
        Item reciveItem = itemRepository.findById(id).orElseThrow(()
                -> new NotFoundException("Item with id = " + id + " not found"));

        //ownership check
        if (ownerId == reciveItem.getOwner().getId()) {
            if ((item.getName() != null) && (!item.getName().isBlank())) {
                reciveItem.setName(item.getName());
            }
            if ((item.getDescription() != null) && (!item.getDescription().isBlank())) {
                reciveItem.setDescription(item.getDescription());
            }
            if (item.getAvailable() != null) {
                reciveItem.setAvailable(item.getAvailable());
            }
            itemRepository.save(reciveItem);
            return ItemMapper.toItemDto(reciveItem);
        } else
            throw new NotFoundException("It is not possible to edit this item on behalf of the user with id = " + ownerId);
    }

    @Override
    public List<ItemDto> search(String text) {
        return itemRepository.searchByText(text.toLowerCase()).stream()
                .map(ItemMapper::toItemDto)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(long id) {
        itemRepository.deleteById(id);
    }
}
