package ru.practicum.shareit.request.model;

import lombok.experimental.UtilityClass;
import ru.practicum.shareit.request.model.dto.ItemRequestDtoRQ;
import ru.practicum.shareit.request.model.dto.ItemRequestDtoRS;
import ru.practicum.shareit.user.model.User;

import java.util.ArrayList;

@UtilityClass
public class ItemRequestMapper {
    public static ItemRequest toItemRequest(ItemRequestDtoRQ itemRequestDtoRQ, User requester) {
        return ItemRequest.builder()
                .requester(requester)
                .description(itemRequestDtoRQ.getDescription())
                .created(itemRequestDtoRQ.getCreated())
                .build();
    }

    public static ItemRequestDtoRQ toItemRequestDtoRQ(ItemRequest itemRequest) {
        return ItemRequestDtoRQ.builder()
                .id(itemRequest.getId())
                .requesterId(itemRequest.getRequester().getId())
                .description(itemRequest.getDescription())
                .created(itemRequest.getCreated())
                .build();
    }

    public static ItemRequestDtoRS toItemRequestDtoRS(ItemRequest itemRequest) {
        return ItemRequestDtoRS.builder()
                .id(itemRequest.getId())
                .requesterId(itemRequest.getRequester().getId())
                .description(itemRequest.getDescription())
                .created(itemRequest.getCreated())
                .items(new ArrayList<>())
                .build();
    }
}