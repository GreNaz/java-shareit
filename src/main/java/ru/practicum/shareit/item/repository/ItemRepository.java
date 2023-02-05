package ru.practicum.shareit.item.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

@RepositoryRestResource
public interface ItemRepository extends JpaRepository<Item, Long> {
    @Query("select item from Item item " +
            "where item.available = true " +
            "and (lower(item.name) like %?1% " +
            "or lower(item.description) like %?1%)")
    List<Item> searchByText(String text);
}
