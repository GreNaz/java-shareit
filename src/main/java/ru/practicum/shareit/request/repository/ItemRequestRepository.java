package ru.practicum.shareit.request.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.request.model.ItemRequest;

import java.util.List;

@Repository
public interface ItemRequestRepository extends JpaRepository<ItemRequest, Long> {
    List<ItemRequest> findAllByRequesterId(long userId);

    @Query("select item from Item item " +
            "where item.itemRequest.id in :ids")
    List<Item> searchByRequestsId(@Param("ids") List<Long> ids);

    @Query("select itemRequest from ItemRequest itemRequest " +
            "where itemRequest.requester.id != ?1")
    List<ItemRequest> findAllPageable(long userId, Pageable pageable);
}