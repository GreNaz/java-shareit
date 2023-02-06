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

import java.util.List;

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

        //похоже это должно быть в сервисе,
        //но работа с преобразованием в объект дожна быть тут
        //я в замешательстве

        Item item = itemRepository.findById(bookingDtoRQ.getItemId()).orElseThrow(() -> {
            throw new NotFoundException("Item with id = " + bookingDtoRQ.getItemId() + " not found");
        });
        User user = userRepository.findById(userId).orElseThrow(() -> {
            throw new NotFoundException("User with id = " + userId + " not found");
        });

        return bookingService.create(userId, BookingMapper.toBooking(bookingDtoRQ, item, user));
    }

    @PatchMapping("/{bookingId}")
    public BookingDtoRS update(
            @PathVariable long bookingId,
            @RequestParam boolean approved,
            @RequestHeader(name = "X-Sharer-User-Id") long ownerId) {
        log.info("Received a request to update the booking");
        return bookingService.update(bookingId, ownerId, approved);
    }

    @GetMapping("/{bookingId}")
    public List<BookingDtoRS> getBooking(
            @RequestParam(defaultValue = "ALL") String state,
            @PathVariable long bookingId,
            @RequestHeader(name = "X-Sharer-User-Id") long ownerId) {
        log.info("Received a request to get bookings with state {}", state);
        return bookingService.getWithState(bookingId, ownerId, state);
    }

    @GetMapping("/owner")
    public List<BookingDtoRS> getBookingFromOwner(
            @RequestParam(defaultValue = "ALL") String state,
            @RequestHeader(name = "X-Sharer-User-Id") long ownerId) {
        log.info("Received a request to get bookings with state {}", state);
        return bookingService.getBookingFromOwner(ownerId, state);
    }
}
