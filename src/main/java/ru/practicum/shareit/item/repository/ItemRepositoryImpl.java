package ru.practicum.shareit.item.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.item.model.dto.ItemDto;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ItemRepositoryImpl implements ItemRepository {
    private static long itemId = 0L;
    private final HashMap<Integer, ItemDto> items = new HashMap<>();

    @Override
    public List<ItemDto> get() {
        return null;
    }

    @Override
    public Optional<ItemDto> get(long id) {
        return Optional.empty();
    }

    @Override
    public ItemDto create(ItemDto itemDto) {
        return null;
    }

    @Override
    public ItemDto update(long id, ItemDto itemDto) {
        return null;
    }

    @Override
    public void delete(long id) {

    }
}
