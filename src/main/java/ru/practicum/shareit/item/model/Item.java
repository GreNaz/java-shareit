package ru.practicum.shareit.item.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.practicum.shareit.request.model.ItemRequest;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class Item {

    private long id;

    private String name;
    private String description;
    private Boolean available;
    private long owner;
    private ItemRequest request;
}
