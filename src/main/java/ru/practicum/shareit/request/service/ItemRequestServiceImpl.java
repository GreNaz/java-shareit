package ru.practicum.shareit.request.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.error.exception.NotFoundException;
import ru.practicum.shareit.item.model.ItemMapper;
import ru.practicum.shareit.item.model.dto.ItemDto;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.request.model.ItemRequestMapper;
import ru.practicum.shareit.request.model.dto.ItemRequestDtoRQ;
import ru.practicum.shareit.request.model.dto.ItemRequestDtoRS;
import ru.practicum.shareit.request.repository.ItemRequestRepository;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ItemRequestServiceImpl implements ItemRequestService {
    private final ItemRepository itemRepository;
    private final ItemRequestRepository requestRepository;
    private final UserRepository userRepository;

    @Override
    public ItemRequestDtoRS create(long userId, ItemRequestDtoRQ itemRequestDtoRQ) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));
        itemRequestDtoRQ.setCreated(LocalDateTime.now());
        ItemRequest itemRequest = requestRepository.save(ItemRequestMapper.toItemRequest(itemRequestDtoRQ, user));
        log.info("Request created");
        return ItemRequestMapper.toItemRequestDtoRS(itemRequest);
    }

    @Override
    public List<ItemRequestDtoRS> getInfo(long userId) {
        userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));
        List<ItemRequestDtoRS> responseList = requestRepository.findAllByRequesterId(userId).stream()
                .map(ItemRequestMapper::toItemRequestDtoRS)
                .collect(Collectors.toList());
        return setItemsToRequests(responseList);
    }

    @Override
    public ItemRequestDtoRS getInfo(long userId, long requestId) {
        userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));

        ItemRequest itemRequest = requestRepository.findById(requestId).orElseThrow(() ->
                new NotFoundException("Request not found"));

        List<ItemDto> items = itemRepository.findByItemRequestId(requestId).stream()
                .map(ItemMapper::toItemDto)
                .collect(Collectors.toList());
        ItemRequestDtoRS itemRequestDtoResponse = ItemRequestMapper.toItemRequestDtoRS(itemRequest);
        itemRequestDtoResponse.setItems(items);
        return itemRequestDtoResponse;
    }

    @Override
    public List<ItemRequestDtoRS> getRequests(long userId, int from, int size) {
        int page = from / size;
        PageRequest pr = PageRequest.of(page, size);
        List<ItemRequestDtoRS> responseList = requestRepository.findAllPageable(userId, pr).stream()
                .map(ItemRequestMapper::toItemRequestDtoRS)
                .collect(Collectors.toList());
        return setItemsToRequests(responseList);
    }

    private List<ItemRequestDtoRS> setItemsToRequests(List<ItemRequestDtoRS> itemRequestDtoRS) {
        Map<Long, ItemRequestDtoRS> requests = itemRequestDtoRS.stream()
                .collect(Collectors.toMap(ItemRequestDtoRS::getId, x -> x, (a, b) -> b));
        List<Long> ids = requests.values().stream()
                .map(ItemRequestDtoRS::getId)
                .collect(Collectors.toList());
        List<ItemDto> items = itemRepository.searchByRequestsId(ids).stream()
                .map(ItemMapper::toItemDto)
                .collect(Collectors.toList());

        items.forEach(itemDto -> {
            long id = itemDto.getRequestId();
            requests.get(id).getItems().add(itemDto);
        });

        return new ArrayList<>(requests.values());
    }
}