package ru.practicum.shareit.item.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.error.exception.ConflictException;
import ru.practicum.shareit.item.model.Item;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class ItemRepositoryImpl implements ItemRepository {
    private long itemId = 0L;
    private final Map<Long, Item> items = new HashMap<>();

    @Override
    public List<Item> get() {
        return List.copyOf(items.values());
    }

    @Override
    public Optional<Item> get(long id) {
        return Optional.ofNullable(items.get(id));
    }

    @Override
    public Item create(long userId, Item item) {
        if (items.containsValue(item)) {
            throw new ConflictException("Item = " + item + " already exist");
        }
        item.setOwner(userId);
        item.setId(++itemId);
        items.put(item.getId(), item);
        return item;
    }

    @Override
    public Item update(long id, Item item, long ownerId) {
        return null;
        //вынес логику как и попросили
    }

    @Override
    public void delete(long id) {
        items.remove(id);
    }

    @Override
    public List<Item> search(String text) {
        return items.values().stream()
                .filter(item -> item.getName().toLowerCase().contains(text.toLowerCase()) ||
                        item.getDescription().toLowerCase().contains(text.toLowerCase()))
                .filter(Item::getAvailable)
                .collect(Collectors.toList());
    }
}
