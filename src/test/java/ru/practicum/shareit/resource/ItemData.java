package ru.practicum.shareit.resource;

import ru.practicum.shareit.item.model.dto.ItemDto;

public class ItemData {

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
