package ru.practicum.shareit.item.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.error.exception.ConflictException;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.model.ItemMapper;
import ru.practicum.shareit.item.model.dto.ItemDto;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static ru.practicum.shareit.item.model.ItemMapper.toItem;

@Repository
@RequiredArgsConstructor
public class ItemRepositoryImpl implements ItemRepository {
    private static long itemId = 0L;
    private final HashMap<Long, Item> items = new HashMap<>();

    @Override
    public List<ItemDto> get() {
        return List.copyOf(
                items.values().stream()
                        .map(ItemMapper::toItemDto)
                        .collect(Collectors.toList())
        );
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
        itemDto.setId(++itemId);
        items.put(itemDto.getId(), toItem(itemDto));
        return itemDto;
    }

    @Override
    public ItemDto update(long id, ItemDto itemDto) {
        itemDto.setId(id);
        items.put(id, toItem(itemDto));
        return itemDto;
    }

    @Override
    public List<ItemDto> search(String text) {
        return null;
    }

    @Override
    public void delete(long id) {
        items.remove(id);
    }
}
