package ru.practicum.shareit.booking.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.BookingMapper;
import ru.practicum.shareit.booking.model.dto.BookingDtoRQ;
import ru.practicum.shareit.booking.model.dto.BookingDtoRS;
import ru.practicum.shareit.booking.service.BookingService;
import ru.practicum.shareit.error.exception.NotFoundException;
import ru.practicum.shareit.error.validation.Create;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/bookings")
public class BookingController {
    private final BookingService bookingService;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;

    @PostMapping
    public BookingDtoRS create(
            @RequestHeader(name = "X-Sharer-User-Id") long userId,
            @Validated(Create.class) @RequestBody BookingDtoRQ bookingDtoRQ) {

        log.info("Received a request to add a booking");

        Item item = itemRepository.findById(bookingDtoRQ.getItemId()).orElseThrow(() -> {
            throw new NotFoundException("Item with id = " + bookingDtoRQ.getItemId() + " not found");
        });
        User user = userRepository.findById(userId).orElseThrow(() -> {
            throw new NotFoundException("User with id = " + userId + " not found");
        });

        return bookingService.create(userId, BookingMapper.toBooking(bookingDtoRQ, item, user));
    }

//    @PatchMapping("/{itemId}")
//    public ItemDto update(
//            @PathVariable long itemId,
//            @RequestBody ItemDto itemDto,
//            @RequestHeader(name = "X-Sharer-User-Id") long ownerId) {
//        log.info("Received a request to update the item");
//        return itemService.update(itemId, ItemMapper.toItem(itemDto), ownerId);
//    }
//
//    @GetMapping("{itemId}")
//    public ItemDto getItem(
//            @PathVariable long itemId) {
//        log.info("Received a request to get Item with id {}", itemId);
//        return itemService.getItem(itemId);
//    }
//
//    @GetMapping
//    public List<ItemDto> getUserItems(
//            @RequestHeader(name = "X-Sharer-User-Id") long ownerId) {
//        log.info("Received a request to get Items from User with id = {}", ownerId);
//        return itemService.getUserItems(ownerId);
//    }
//
//    @GetMapping("/search")
//    public List<ItemDto> search(
//            @RequestHeader(name = "X-Sharer-User-Id") long ownerId,
//            @RequestParam String text) {
//        log.info("Received a request to search by query = {}", text);
//        if (text.isBlank()) {
//            return Collections.emptyList();
//        } else {
//            return itemService.search(text);
//        }
//    }
//
//    @DeleteMapping("/{itemId}")
//    public void delete(
//            @PathVariable long itemId) {
//        log.info("Received a request to delete Item with id {} ", itemId);
//        itemService.delete(itemId);
//    }
}
