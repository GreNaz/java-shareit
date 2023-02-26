package ru.practicum.shareit.booking;

import lombok.experimental.UtilityClass;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.dto.BookingDtoCreate;
import ru.practicum.shareit.booking.model.dto.BookingDtoFullResponse;
import ru.practicum.shareit.booking.model.dto.BookingDtoResponse;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

@UtilityClass
public class BookingMapper {
    public static BookingDtoResponse toBookingDtoResponse(Booking booking) {
        return BookingDtoResponse.builder()
                .id(booking.getId())
                .start(booking.getStart())
                .end(booking.getEnd())
                .status(booking.getStatus())
                .itemId(booking.getItem().getId())
                .bookerId(booking.getBooker().getId())
                .build();
    }

    public static Booking toBooking(BookingDtoCreate bookingDtoCreate, Item inputItem, User inputUser) {
        return Booking.builder()
                .id(bookingDtoCreate.getId())
                .start(bookingDtoCreate.getStart())
                .end(bookingDtoCreate.getEnd())
                .item(inputItem)
                .booker(inputUser)
                .build();
    }

    public static BookingDtoFullResponse toBookingRS(Booking booking) {
        return BookingDtoFullResponse.builder()
                .id(booking.getId())
                .start(booking.getStart())
                .end(booking.getEnd())
                .status(booking.getStatus())
                .item(booking.getItem())
                .booker(booking.getBooker())
                .build();
    }
}
