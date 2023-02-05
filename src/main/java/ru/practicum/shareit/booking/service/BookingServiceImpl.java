package ru.practicum.shareit.booking.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.BookingMapper;
import ru.practicum.shareit.booking.BookingStatus;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.dto.BookingDtoRS;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.error.exception.BadRequestException;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;

    @Override
    public BookingDtoRS create(long userId, Booking booking) {

        if (userId == booking.getItem().getOwner().getId()) {
            throw new BadRequestException("Unable to book your own");
        }

        if (Boolean.FALSE.equals(booking.getItem().getAvailable())) {
            throw new BadRequestException("Item not available for booking now");
        }

        if (booking.getEnd().isBefore(booking.getStart())) {
            throw new BadRequestException("Wrong time to book this item");
        }

        booking.setStatus(BookingStatus.WAITING);

        return BookingMapper.toBookingRS(bookingRepository.save(booking));
    }

    @Override
    public BookingDtoRS update(long id, Booking booking, long ownerId) {
        return null;
    }

    @Override
    public void delete(long id) {
    }
}
