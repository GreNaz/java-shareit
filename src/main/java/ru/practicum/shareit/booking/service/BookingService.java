package ru.practicum.shareit.booking.service;

import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.dto.BookingDtoRS;

import java.util.List;

public interface BookingService {

    BookingDtoRS create(long userId, Booking booking);

    BookingDtoRS update(long bookingId, long ownerId, boolean approved);

    BookingDtoRS get(long bookingId, long ownerId);

    List<BookingDtoRS> getBookings(long ownerId, String state);

    List<BookingDtoRS> getBookingFromOwner(long ownerId, String state);

}
