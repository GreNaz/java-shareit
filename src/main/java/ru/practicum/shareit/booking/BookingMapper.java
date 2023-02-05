package ru.practicum.shareit.booking;

import lombok.experimental.UtilityClass;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.dto.BookingDtoRQ;
import ru.practicum.shareit.booking.model.dto.BookingDtoRS;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

@UtilityClass
public class BookingMapper {
    public static BookingDtoRQ toBookingDto(Booking booking) {
        return BookingDtoRQ.builder()
                .id(booking.getId())
                .start(booking.getStart())
                .end(booking.getEnd())
                .status(booking.getStatus())
                .bookerId(booking.getBooker().getId())
                .itemId(booking.getItem().getId())
                .build();
    }

    public static Booking toBooking(BookingDtoRQ bookingDtoRQ, Item inputItem, User inputUser) {
        return Booking.builder()
                .id(bookingDtoRQ.getId())
                .start(bookingDtoRQ.getStart())
                .end(bookingDtoRQ.getEnd())
                .status(bookingDtoRQ.getStatus())
                .item(inputItem)
                .booker(inputUser)
                .build();
    }

    public static BookingDtoRS toBookingRS(Booking booking) {
        return BookingDtoRS.builder()
                .id(booking.getId())
                .start(booking.getStart())
                .end(booking.getEnd())
                .status(booking.getStatus())
                .item(booking.getItem())
                .booker(booking.getBooker())
                .build();
    }
}
