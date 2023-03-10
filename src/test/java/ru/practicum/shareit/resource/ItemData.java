package ru.practicum.shareit.resource;

import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.model.dto.ItemDto;
import ru.practicum.shareit.user.model.User;

public class ItemData {
    public static Item getItem() {
        return Item.builder()
                .id(1L)
                .name("name")
                .description("description")
                .available(true)
                .owner(null)
                .itemRequest(null)
                .build();
    }

    public static ItemDto getItemDto() {
        return ItemDto.builder()
                .id(1L)
                .name("name")
                .description("description")
                .available(true)
                .requestId(null)
                .build();
    }

    public static ItemDto getItemDtoForUpdate() {
        return ItemDto.builder()
                .id(1L)
                .name("updated")
                .description("updated description")
                .available(true)
                .requestId(null)
                .build();
    }
}
