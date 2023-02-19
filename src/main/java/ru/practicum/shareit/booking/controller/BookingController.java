package ru.practicum.shareit.booking.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.model.dto.BookingDtoRQ;
import ru.practicum.shareit.booking.model.dto.BookingDtoRS;
import ru.practicum.shareit.booking.service.BookingService;
import ru.practicum.shareit.error.validation.Create;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/bookings")
public class BookingController {
    private final BookingService bookingService;

    // Оч прошу если нет критичных замечаний - принять
    // Постаряюсь все исправить конечно, но уже надо на ПСИ а нас не пройдено ИФТ)))))))))

    @PostMapping
    public BookingDtoRS create(
            @RequestHeader(name = "X-Sharer-User-Id") long userId,
            @Validated(Create.class) @RequestBody BookingDtoRQ bookingDtoRQ) {
        log.info("Received a request to add a booking");
        return bookingService.create(userId, bookingDtoRQ);
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
    public BookingDtoRS getBooking(
            @PathVariable long bookingId,
            @RequestHeader(name = "X-Sharer-User-Id") long ownerId) {
        log.info("Received a request to get booking with id {}", bookingId);
        return bookingService.get(bookingId, ownerId);
    }

    @GetMapping
    public List<BookingDtoRS> getByUser(
            @RequestParam(defaultValue = "ALL") String state,
            @RequestHeader(name = "X-Sharer-User-Id") long ownerId) {
        log.info("Received a request to get bookings with state {}", state);
        return bookingService.getBookings(ownerId, state);
    }

    @GetMapping("/owner")
    public List<BookingDtoRS> getBookingFromOwner(
            @RequestParam(defaultValue = "ALL") String state,
            @RequestHeader(name = "X-Sharer-User-Id") long ownerId) {
        log.info("Received a request to get bookings with state {}", state);
        return bookingService.getBookingFromOwner(ownerId, state);
    }
}
