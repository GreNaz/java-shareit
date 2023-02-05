package ru.practicum.shareit.booking.service;

import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.dto.BookingDtoRS;

public interface BookingService {

    BookingDtoRS create(long userId, Booking booking);

    BookingDtoRS update(long id, Booking booking, long ownerId);

    void delete(long id);
}
