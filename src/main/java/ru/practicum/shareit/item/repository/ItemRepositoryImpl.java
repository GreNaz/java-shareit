package ru.practicum.shareit.item.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.error.exception.ConflictException;
import ru.practicum.shareit.item.model.Item;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
        Item updatingItem = items.get(id);
        if ((item.getName() != null) && (!item.getName().isBlank())) {
            updatingItem.setName(item.getName());
        }
        if ((item.getDescription() != null) && (!item.getDescription().isBlank())) {
            updatingItem.setDescription(item.getDescription());
        }
        if (item.getAvailable() != null) {
            updatingItem.setAvailable(item.getAvailable());
        }
        return updatingItem;
    }

    @Override
    public void delete(long id) {
        items.remove(id);
    }
}
