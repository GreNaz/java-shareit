package ru.practicum.shareit.booking.service;

import ru.practicum.shareit.booking.model.dto.BookingDtoCreate;
import ru.practicum.shareit.booking.model.dto.BookingDtoFullResponse;

import java.util.List;

public interface BookingService {
    BookingDtoFullResponse create(long userId, BookingDtoCreate bookingDtoCreate);

    BookingDtoFullResponse update(long bookingId, long ownerId, boolean approved);

    BookingDtoFullResponse get(long bookingId, long ownerId);

    List<BookingDtoFullResponse> getBookings(long ownerId, String state);

    List<BookingDtoFullResponse> getBookingFromOwner(long ownerId, String state);

}
