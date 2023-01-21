package ru.practicum.shareit.item.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.error.exception.ConflictException;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.ItemMapper;
import ru.practicum.shareit.item.dto.ItemDto;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static ru.practicum.shareit.item.ItemMapper.toItem;

@Repository
@RequiredArgsConstructor
public class ItemRepositoryImpl implements ItemRepository {
    private static long itemId = 0L;
    private final HashMap<Long, Item> items = new HashMap<>();

    @Override
    public List<Item> get() {
        return List.copyOf(items.values());
    }

    @Override
    public Optional<ItemDto> get(long id) {
        return Optional.ofNullable(
                ItemMapper.toItemDto(items.get(id))
        );
    }

    @Override
    public ItemDto create(long userId, ItemDto itemDto) {
        if (items.containsValue(toItem(itemDto))) {
            throw new ConflictException("Item = " + itemDto + " already exist");
        }
        Item item = toItem(itemDto);
        item.setOwner(userId);
        item.setId(++itemId);
        items.put(item.getId(), item);
        return ItemMapper.toItemDto(item);
    }

    @Override
    public ItemDto update(long id, ItemDto itemDto, long ownerId) {
        Item updatingItem = items.get(id);
        if (itemDto.getName() != null) {
            updatingItem.setName(itemDto.getName());
        }
        if (itemDto.getDescription() != null) {
            updatingItem.setDescription(itemDto.getDescription());
        }
        if (itemDto.getAvailable() != null) {
            updatingItem.setAvailable(itemDto.getAvailable());
        }
        items.put(id, updatingItem);
        return ItemMapper.toItemDto(updatingItem);
    }

    @Override
    public void delete(long id) {
        items.remove(id);
    }
}
