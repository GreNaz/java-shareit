package ru.practicum.shareit.item.model;


import lombok.experimental.UtilityClass;
import ru.practicum.shareit.item.model.dto.ItemDto;

@UtilityClass
public class ItemMapper {
    public static ItemDto toItemDto(Item item) {
        return ItemDto.builder()
                .id(item.id)
                .name(item.name)
                .description(item.description)
                .available(item.available)
                .build();
    }

    public static Item toItem(ItemDto itemDto) {
        return Item.builder()
                .name(itemDto.getName())
                .description(itemDto.getDescription())
                .available(itemDto.getAvailable())
                .build();
    }
}
