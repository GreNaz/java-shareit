package ru.practicum.shareit.booking.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.model.dto.BookingDtoCreate;
import ru.practicum.shareit.booking.model.dto.BookingDtoFullResponse;
import ru.practicum.shareit.booking.service.BookingService;
import ru.practicum.shareit.error.validation.Create;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/bookings")
public class BookingController {
    private final BookingService bookingService;

    @PostMapping
    public BookingDtoFullResponse create(
            @RequestHeader(name = "X-Sharer-User-Id") long userId,
            @Validated(Create.class) @RequestBody BookingDtoCreate bookingDtoCreate) {
        log.info("Received a request to add a booking");
        return bookingService.create(userId, bookingDtoCreate);
    }

    @PatchMapping("/{bookingId}")
    public BookingDtoFullResponse update(
            @PathVariable long bookingId,
            @RequestParam boolean approved,
            @RequestHeader(name = "X-Sharer-User-Id") long ownerId) {
        log.info("Received a request to update the booking");
        return bookingService.update(bookingId, ownerId, approved);
    }

    @GetMapping("/{bookingId}")
    public BookingDtoFullResponse getBooking(
            @PathVariable long bookingId,
            @RequestHeader(name = "X-Sharer-User-Id") long ownerId) {
        log.info("Received a request to get booking with id {}", bookingId);
        return bookingService.get(bookingId, ownerId);
    }

    @GetMapping
    public List<BookingDtoFullResponse> getByUser(
            @RequestParam(defaultValue = "ALL") String state,
            @RequestHeader(name = "X-Sharer-User-Id") long ownerId) {
        log.info("Received a request to get bookings with state {}", state);
        return bookingService.getBookings(ownerId, state);
    }

    @GetMapping("/owner")
    public List<BookingDtoFullResponse> getBookingFromOwner(
            @RequestParam(defaultValue = "ALL") String state,
            @RequestHeader(name = "X-Sharer-User-Id") long ownerId) {
        log.info("Received a request to get bookings with state {}", state);
        return bookingService.getBookingFromOwner(ownerId, state);
    }
}
